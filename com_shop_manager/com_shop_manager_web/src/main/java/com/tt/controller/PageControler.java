package com.tt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageControler {

    /**
     * 网站首页面
     * @return
     */
    @RequestMapping("/")
    public String getindex(){
        return "index";
    }

    /**
     * 跳转各个页面
     * @param page
     * @return
     */
    @RequestMapping("/{page}")
    public  String getpage(@PathVariable String page){
        return page;
    }

}
