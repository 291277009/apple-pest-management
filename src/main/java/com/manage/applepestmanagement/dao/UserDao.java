package com.manage.applepestmanagement.dao;

import com.manage.applepestmanagement.po.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * User: Swang
 * Date: 2018-12-06
 * Time: 10:20
 */
@Mapper
public interface UserDao {

    @Select("SELECT * FROM user WHERE name = #{name}")
    User findUserByName(@Param("name") String name);

    @Select("SELECT * FROM user")
    List<User> findAllUser();

    @Insert("INSERT INTO user(name, password, age, money) VALUES(#{user.name}, #{user.password}, #{user.age}, #{user.money})")
    void insertUser(@Param("user") User user);

    @Delete("DELETE FROM user WHERE id = #{id}")
    void deleteUser(@Param("id") Integer id);

    @Update("UPDATE user SET name = #{user.name},age = #{user.age}, money = #{user.money} WHERE id = #{user.id}")
    void updateUserById(@Param("user") User user);
}
