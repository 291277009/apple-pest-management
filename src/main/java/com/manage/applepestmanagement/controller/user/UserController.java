package com.manage.applepestmanagement.controller.user;

import com.manage.applepestmanagement.service.UserService;
import com.manage.applepestmanagement.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * User: Swang
 * Date: 2018-12-21
 * Time: 15:19
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisUtil redisUtil;

    @GetMapping("/doLogin")
    public String login() {
        return "login";
    }


}
