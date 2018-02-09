package com.tt.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author sjg
 */
@Controller
@RequestMapping("/page")
public class PageController {

    //打开注册页面
    @RequestMapping("/register")
    public String showRegister(){
        return "register";
    }

    //打开登录页面
    @RequestMapping("/login")
    public String showLogin(String redirect, Model model){
        model.addAttribute("redirect",redirect);
        return "login";
    }


}
