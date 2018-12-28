package com.manage.applepestmanagement.po;

import lombok.Data;

import java.io.Serializable;

/**
 * User: Swang
 * Date: 2018-12-06
 * Time: 10:19
 */
@Data
public class User implements Serializable {

    private static final long serialVersionUID = -1623415533274240956L;
    private int id;
    private String name;
    private String password;
    private int age;
    private double money;
}
