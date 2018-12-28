package com.manage.applepestmanagement.common.network;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.manage.applepestmanagement.utils.DateFormatUtil;

/**
 * 数据结果格式
 *
 * @author wx
 */
public class DataResult {

    /**
     * 返回json格式<br/>
     * 参数：<br/>
     * status：状态描述，例如 success，error<br/>
     * code：状态码，例如 10000，20101<br/>
     * timestamp: 时间戳，例如 2018-01-01 08:00:00<br/>
     * data: 数据内容，JSON格式
     */
    private JSONObject result;

    /**
     * 基础构造方法
     *
     * @param code      状态码，枚举ResultCode型
     * @param timestamp 时间戳，例如 2018-01-01 08:00:00
     * @param array     数据内容，JSONObject格式
     */
    public DataResult(ResultCode code, String timestamp, JSONObject object) {
        assembleDataResult(code.status(), code.code(), timestamp, object);
    }

    /**
     * 基础构造方法
     *
     * @param code      状态码，枚举ResultCode型
     * @param timestamp 时间戳，例如 2018-01-01 08:00:00
     * @param array     数据内容，JSONArray格式
     */
    public DataResult(ResultCode code, String timestamp, JSONArray array) {
        assembleDataResult(code.status(), code.code(), timestamp, array);
    }

    /**
     * 构造方法（缺省时间戳）
     *
     * @param code  状态码，枚举ResultCode型
     * @param array 数据内容，JSONObject格式
     */
    public DataResult(ResultCode code, JSONObject object) {
        assembleDataResult(code.status(), code.code(), DateFormatUtil.toString(System.currentTimeMillis()), object);
    }

    /**
     * 构造方法（缺省时间戳）
     *
     * @param code  状态码，枚举ResultCode型
     * @param array 数据内容，JSONArray格式
     */
    public DataResult(ResultCode code, JSONArray array) {
        assembleDataResult(code.status(), code.code(), DateFormatUtil.toString(System.currentTimeMillis()), array);
    }

    /**
     * 构造方法（缺省时间戳）
     *
     * @param code 状态码，枚举ResultCode型
     */
    public DataResult(ResultCode code) {
        assembleDataResult(code.status(), code.code(), DateFormatUtil.toString(System.currentTimeMillis()), null);
    }

    /**
     * 组装json返回对象
     *
     * @param status    状态描述，例如 success，error
     * @param code      状态码，例如 10000，20101
     * @param timestamp 时间戳，例如 2018-01-01 08:00:00
     * @param data      数据内容，JSON格式
     * @return
     */
    private void assembleDataResult(String status, int code, String timestamp, Object data) {
        result = new JSONObject();
        result.put("status", status);
        result.put("code", code);
        result.put("timestamp", timestamp);
        if (data != null) {
            result.put("content", chooseDataType(data));
        }
    }

    /**
     * 根据数据类型返回JSON数据对象
     *
     * @param data
     * @return
     */
    private JSONObject chooseDataType(Object data) {
        JSONObject dataResult = new JSONObject();
        if (JSONObject.class.isInstance(data)) {
            dataResult.put("type", ResultDataType.OBJECT.toString());
        } else if (JSONArray.class.isInstance(data)) {
            dataResult.put("type", ResultDataType.ARRAY.toString());
        }
        dataResult.put("data", data);
        return dataResult;
    }

    /**
     * 返回json对象
     *
     * @return
     */
    public JSONObject toJSON() {
        return this.result;
    }

    /**
     * 返回JSON对象中插入所需信息
     *
     * @param key   属性key
     * @param value 属性值
     * @return
     */
    protected JSONObject addValue(String key, Object value) {
        this.result.put(key, value);
        return this.result;
    }

    /**
     * 返回成功数据
     *
     * @return
     */
    public static JSONObject ok() {
        DataResult result = new DataResult(ResultCode.C10000);
        return result.toJSON();
    }

    /**
     * 返回成功数据
     *
     * @param data JSONArray类型数据
     * @return
     */
    public static JSONObject ok(JSONArray data) {
        DataResult result = new DataResult(ResultCode.C10000, data);
        return result.toJSON();
    }

    /**
     * 返回成功数据
     *
     * @param data JSONObject类型数据
     * @return
     */
    public static JSONObject ok(JSONObject data) {
        DataResult result = new DataResult(ResultCode.C10000, data);
        return result.toJSON();
    }

    /**
     * 返回失败数据
     *
     * @return
     */
    public static JSONObject error() {
        DataResult result = new DataResult(ResultCode.C20000);
        return result.toJSON();
    }

}
