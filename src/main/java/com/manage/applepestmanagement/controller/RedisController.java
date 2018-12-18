package com.manage.applepestmanagement.controller;

import com.manage.applepestmanagement.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * User: Swang
 * Date: 2018-12-08
 * Time: 11:44
 */
@RestController
@RequestMapping("/redis")
@Slf4j
public class RedisController {

    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping("/getRedis")
    public String getRedis() {
        redisUtil.set("20182018","这是一条测试数据", 1);
        Long resExpire = redisUtil.expire("20182018", 60, 1);//设置key过期时间
        log.info("resExpire="+resExpire);
        String res = redisUtil.get("20182018", 1);
        return res;
    }
}
