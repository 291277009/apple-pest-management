package com.manage.applepestmanagement.controller.user;

import com.manage.applepestmanagement.po.User;
import com.manage.applepestmanagement.service.UserService;
import com.manage.applepestmanagement.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

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

    @PostMapping("/doLogin")
    public String login(String name, String password, HttpSession session) {
        name = MD5Util.string2MD5(name);
        password = MD5Util.string2MD5(password);
        User user = userService.selectUserByName(name);
        if (user != null && password.equals(user.getPassword())) {
            session.setAttribute(session.getId(), user);
            return "index";
        }
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(session.getId());
        return "login";
    }

    @PostMapping("/register")
    public String register(String name, String password, ModelMap modelMap) {
        name = MD5Util.string2MD5(name);
        password = MD5Util.string2MD5(password);
        if (userService.selectUserByName(name) != null) {
            modelMap.addAttribute("message", "用户名已存在");
            return "login";
        }
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        userService.addUser(user);
        return "login";
    }

}
