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

    @GetMapping("/chart")
    public String chartController() {
        return "chart";
    }

    @GetMapping("/empty")
    public String emptyController() {
        return "empty";
    }

    @GetMapping("/table")
    public String tableController() {
        return "table";
    }

    @GetMapping("/form")
    public String formController() {
        return "form";
    }

    @GetMapping("/ui-elements")
    public String elementsController() {
        return "ui-elements";
    }

    @GetMapping("/tab-panel")
    public String tabController() {
        return "tab-panel";
    }
}
