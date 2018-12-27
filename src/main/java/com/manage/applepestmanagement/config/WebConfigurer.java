package com.manage.applepestmanagement.config;

import com.manage.applepestmanagement.config.interceptors.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * User: Swang
 * Date: 2018-12-21
 * Time: 14:53
 */
@Configuration
public class WebConfigurer implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    //用来配置静态资源
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

    }

    //用来注册拦截器，从此方法添加拦截器激活
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns("/login", "/register");
    }
}
