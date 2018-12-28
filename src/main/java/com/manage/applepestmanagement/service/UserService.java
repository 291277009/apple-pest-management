package com.manage.applepestmanagement.service;

import com.manage.applepestmanagement.dao.UserDao;
import com.manage.applepestmanagement.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: Swang
 * Date: 2018-12-06
 * Time: 10:34
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public User selectUserByName(String name) {
        return userDao.findUserByName(name);
    }

    public List<User> selectAllUser() {
        return userDao.findAllUser();
    }

    public User addUser(User user) {
        return userDao.insertUser(user);
    }

    public void removeUser(Integer id) {
        userDao.deleteUser(id);
    }
}
