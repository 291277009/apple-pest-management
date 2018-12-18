package com.manage.applepestmanagement.service;

import com.manage.applepestmanagement.po.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * User: Swang
 * Date: 2018-12-06
 * Time: 11:03
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void selectUserByName() {
        User user = userService.selectUserByName("wang");
        log.info(user.toString());
        Assert.assertEquals(1, user.getId());
    }

    @Test
    public void selectAllUser() {
        List<User> userList = userService.selectAllUser();
        log.info(userList.toString());
        Assert.assertEquals(1, userList.size());
    }

    @Test
    public void addUser() {
        User user = new User();
        user.setId(2);
        user.setAge(22);
        user.setName("li");
        user.setMoney(110.2);
        userService.addUser(user);
    }

    @Test
    public void removeUser() {
        userService.removeUser(5);
    }
}
