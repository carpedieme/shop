package com.tt.protal.controller;

import com.tt.protal.service.ContentService;
import com.tt.util.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class indexController {

    @Autowired
    private ContentService contentService;

    @RequestMapping("/index")
    public String index(Model model) {
        String jsonstr = contentService.getContentList();
        model.addAttribute("ad1", jsonstr);
        return "index";
    }


//    @RequestMapping(value = "/httpclient/post",method = RequestMethod.POST)
//    @ResponseBody
//    public TaotaoResult  testDoPost(){
//        return TaotaoResult.ok();
//    }

    //produces = MediaType.TEXT_PLAIN_VALUE+";charset=utf-8" 解决乱码
    @RequestMapping(value = "/httpclient/post", method = RequestMethod.POST,produces = MediaType.TEXT_PLAIN_VALUE+";charset=utf-8")
    @ResponseBody
    public String testDoPost(String username, String password) {
        System.out.println(username+"=="+password);
        return "username" + username;
    }
}
