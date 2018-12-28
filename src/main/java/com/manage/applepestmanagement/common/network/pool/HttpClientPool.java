package com.manage.applepestmanagement.common.network.pool;

import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * HttpClient连接池
 * @author wx
 *
 */
public class HttpClientPool {

	private static final Logger logger = LoggerFactory.getLogger(HttpClientPool.class);

	/**
	 * 默认请求端口
	 */
	private static final int DEFAULT_HTTP_PORT = 80;

	/**
	 * 默认编码
	 */
	private static final String DEFAULT_ENCODE = "UTF-8";
	
	private static final int SYS_DEFAULT_MAX_PER_ROUTE = 20;
	private static final int SYS_MAX_TOTAL = 100;
	private static final int SYS_KEEP_ALIVE_TIME = 15000;
	private static final int SYS_TIME_OUT = 3000;

	/**
	 * 每个路由最大连接数
	 */
	private int defaultMaxPerRoute;

	/**
	 * 最大连接数
	 */
	private int maxTotal;

	/**
	 * 连接保持时间，单位毫秒
	 */
	private int keepAliveTime;

	/**
	 * 超时时间，单位毫秒
	 */
	private int timeout;
	
	/**
	 * 创建HttpClient同步锁
	 */
	private Object syncLock = new Object();
	
	private CloseableHttpClient closeableHttpClient;

	public HttpClientPool() {
		this.defaultMaxPerRoute = SYS_DEFAULT_MAX_PER_ROUTE;
		this.maxTotal = SYS_MAX_TOTAL;
		this.keepAliveTime = SYS_KEEP_ALIVE_TIME;
		this.timeout = SYS_TIME_OUT;
	}
	
	public HttpClientPool(int defaultMaxPerRoute, int maxTotal, int keepAliveTime, int timeout) {
		this.defaultMaxPerRoute = defaultMaxPerRoute;
		this.maxTotal = maxTotal;
		this.keepAliveTime = keepAliveTime;
		this.timeout = timeout;
	}
	
	/**
	 * 获取HttpClient
	 * @param url
	 * @return
	 */
	private CloseableHttpClient getHttpClient(String url) {
		String hostName = url.split("/")[2];
		int port = DEFAULT_HTTP_PORT;
		if (hostName.contains(":")) {
			String[] arr = hostName.split(":");
			hostName = arr[0];
			port = Integer.parseInt(arr[1]);
		}
		if (closeableHttpClient == null) {
			synchronized (syncLock) {
				if (closeableHttpClient == null) {
					closeableHttpClient = createHttpClient(hostName, port);
				}
			}
		}
		return closeableHttpClient;
	}
	
	/**
	 * 创建HttpClient
	 * @param hostname
	 * @param port
	 * @return
	 */
	private CloseableHttpClient createHttpClient(String hostName, int port) {

		ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
		LayeredConnectionSocketFactory sslsf = SSLConnectionSocketFactory.getSocketFactory();
		Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("http", plainsf).register("https", sslsf).build();
		PoolingHttpClientConnectionManager clientConnectionManager = new PoolingHttpClientConnectionManager(registry);

		// 设置最大连接数
		clientConnectionManager.setMaxTotal(maxTotal);
		// 设置每个路由的基础连接数
		clientConnectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
		// 可以按需要将目标主机的最大连接数增加
		// HttpHost httpHost = new HttpHost(hostname, port);
		// clientConnectionManager.setMaxPerRoute(new HttpRoute(httpHost), 100);

		// 请求重试处理
		HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {
			@Override
			public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
				// 如果已经重试了5次，就放弃
				if (executionCount >= 5) {
					logger.warn("****[HttpClient连接池]连接超过重试次数5，Host："+hostName);
					return false;
				}
				// 如果服务器丢掉连接，进行重试
				if (exception instanceof NoHttpResponseException) {
					logger.warn("****[HttpClient连接池]连接丢失，Host："+hostName);
					return true;
				}
				// 不要重试SSL握手异常
				if (exception instanceof SSLHandshakeException) {
					logger.warn("****[HttpClient连接池]SSL握手异常，Host："+hostName);
					return false;
				}
				// 连接中断
				if (exception instanceof InterruptedIOException) {
					logger.warn("****[HttpClient连接池]连接中断，Host："+hostName);
					return false;
				}
				// 目标服务器不可达
				if (exception instanceof UnknownHostException) {
					logger.warn("****[HttpClient连接池]未知Host，Host："+hostName);
					return false;
				}
				// 连接超时
				if (exception instanceof ConnectTimeoutException) {
					logger.warn("****[HttpClient连接池]连接超时，Host："+hostName);
					return false;
				}
				// SSL握手异常
				if (exception instanceof SSLException) {
					logger.warn("****[HttpClient连接池]SSL异常，Host："+hostName);
					return false;
				}

				HttpClientContext clientContext = HttpClientContext.adapt(context);
				HttpRequest request = clientContext.getRequest();
				// 如果请求是幂等的，就再次尝试
				if (!(request instanceof HttpEntityEnclosingRequest)) {
					return true;
				}
				return false;
			}
		};
		
		//连接保持设置
		ConnectionKeepAliveStrategy defaultStrategy = new ConnectionKeepAliveStrategy() {
	        @Override
	        public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
	            HeaderElementIterator it = new BasicHeaderElementIterator(response.headerIterator(HTTP.CONN_KEEP_ALIVE));
	            int timeout = keepAliveTime;
	            while (it.hasNext()) {
	                HeaderElement he = it.nextElement();
	                String param = he.getName();
	                String value = he.getValue();
	                if (value != null && param.equalsIgnoreCase("timeout")) {
	                    try {
	                        return Long.parseLong(value) * 1000;
	                    } catch (Exception e) {
	                        e.printStackTrace();
	                        logger.error("****[HttpClient连接池]超时设置转换失败， Exception:" + e.getMessage());
	                    }
	                }
	            }
	            return timeout;
	        }
	    };

		CloseableHttpClient httpClient = HttpClients.custom().setKeepAliveStrategy(defaultStrategy).setConnectionManager(clientConnectionManager)
				.setRetryHandler(httpRequestRetryHandler).build();

		return httpClient;
	}

	/**
	 * 配置request请求的超时等属性
	 * 
	 * @param httpRequestBase
	 */
	private void config(HttpRequestBase httpRequestBase) {
		RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(timeout)
				.setConnectTimeout(timeout).setSocketTimeout(timeout).build();
		httpRequestBase.setConfig(requestConfig);
	}

	/**
	 * 配置Post请求参数
	 * 
	 * @param httPost
	 * @param params
	 */
	private void setPostParams(HttpPost httPost, Map<String, Object> params) {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		Set<String> keySet = params.keySet();
		for (String key : keySet) {
			nvps.add(new BasicNameValuePair(key, params.get(key).toString()));
		}
		try {
			httPost.setEntity(new UrlEncodedFormEntity(nvps, DEFAULT_ENCODE));
		} catch (UnsupportedEncodingException e) {
			logger.error("****[HttpClient连接池]Post参数编码失败，Exception："+e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * post请求
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws IOException
	 */
	public String post(String url, Map<String, Object> params) throws IOException {
		HttpPost httpPost = new HttpPost(url);
		config(httpPost);
		setPostParams(httpPost, params);
		CloseableHttpResponse response = null;
		try {
			response = getHttpClient(url).execute(httpPost, HttpClientContext.create());
			HttpEntity entity = response.getEntity();
			String result = EntityUtils.toString(entity, DEFAULT_ENCODE);
			EntityUtils.consume(entity);
			return result;
		} catch (Exception e) {
			logger.error("****[HttpClient连接池]Post请求结果转换失败，Exception："+e.getMessage());
			throw e;
		} finally {
			try {
				if (response != null)
					response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * get请求
	 * @param url
	 * @return
	 */
	public String get(String url) {
		HttpGet httpget = new HttpGet(url);
		config(httpget);
		CloseableHttpResponse response = null;
		try {
			response = getHttpClient(url).execute(httpget, HttpClientContext.create());
			HttpEntity entity = response.getEntity();
			String result = EntityUtils.toString(entity, DEFAULT_ENCODE);
			EntityUtils.consume(entity);
			return result;
		} catch (IOException e) {
			logger.error("****[HttpClient连接池]get请求结果转换失败，Exception："+e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (response != null)
					response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public int getDefaultMaxPerRoute() {
		return defaultMaxPerRoute;
	}

	public void setDefaultMaxPerRoute(int defaultMaxPerRoute) {
		this.defaultMaxPerRoute = defaultMaxPerRoute;
	}

	public int getMaxTotal() {
		return maxTotal;
	}

	public void setMaxTotal(int maxTotal) {
		this.maxTotal = maxTotal;
	}

	public int getKeepAliveTime() {
		return keepAliveTime;
	}

	public void setKeepAliveTime(int keepAliveTime) {
		this.keepAliveTime = keepAliveTime;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
}
