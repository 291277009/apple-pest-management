package com.manage.applepestmanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * User: Swang
 * Date: 2018-12-21
 * Time: 10:11
 */
@Controller
public class MainController {

    @GetMapping("/index")
    public String indexController() {
        return "index";
    }
}
