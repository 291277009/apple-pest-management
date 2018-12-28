package com.manage.applepestmanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * User: Swang
 * Date: 2018-12-21
 * Time: 10:11
 */
@Controller
public class MainController {

    @RequestMapping("/index")
    public String indexController() {
        return "index";
    }

    @RequestMapping("/charts")
    public String chartsController() {
        return "charts";
    }

    @RequestMapping("/elements")
    public String elementsController() {
        return "elements";
    }

    @RequestMapping("/icons")
    public String iconsController() {
        return "icons";
    }

    @RequestMapping("/login")
    public String loginController() {
        return "login";
    }

    @RequestMapping("/notifications")
    public String notificationsController() {
        return "notifications";
    }

    @RequestMapping("/page-lockscreen")
    public String lockscreenController() {
        return "page-lockscreen";
    }

    @RequestMapping("/page-profile")
    public String profileController() {
        return "page-profile";
    }

    @RequestMapping("/panels")
    public String formController() {
        return "panels";
    }

    @RequestMapping("/tables")
    public String tablesController() {
        return "tables";
    }

    @RequestMapping("/typography")
    public String typographyController() {
        return "typography";
    }

    @RequestMapping("/page-login")
    public String pageLoginController() {
        return "page-login";
    }


}
