package com.Alice.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面跳转的contreller
 * @author Alice
 * @date 2018/8/18/018
 */
@Controller
public class PageController {

    @RequestMapping("/page/{page}")
    public String showRegister(@PathVariable String page){
        return page;
    }

}
