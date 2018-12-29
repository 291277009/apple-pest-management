package com.manage.applepestmanagement.controller.demo;

import com.manage.applepestmanagement.po.User;
import com.manage.applepestmanagement.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * User: Swang
 * Date: 2018-12-06
 * Time: 10:48
 */
@RestController
@RequestMapping("/user1")
public class DemoUserController {

    @Autowired
    private UserService userService;

    @GetMapping("/queryUser")
    public User queryUser(@Param("name") String name) {
        return userService.selectUserByName(name);
    }

    @GetMapping("/queryAll")
    public List<User> queryAll() {
        return userService.selectAllUser();
    }

    @PostMapping("/addUser")
    public void addUser(@Param("user") User user) {
        userService.addUser(user);
    }

    @DeleteMapping("/deleteUser")
    public void deleteUser(@Param("id") Integer id) {
        userService.removeUser(id);
    }
}
