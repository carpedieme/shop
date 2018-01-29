package com.tt.controller;


import com.tt.pojo.TbContent;
import com.tt.service.ContentService;
import com.tt.util.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/content")
public class ContentController {

    @Autowired
    private ContentService contentService;

    /**
     * 接收表单中的内容，使用pojo接收。要求pojo的属性要和表单中的name一致。调用Service插入内容信息。返回TaotaoResult。Json格式的数据。
     * @param content
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public TaotaoResult insertContent(TbContent content){
        return contentService.insertContent(content);
    }
}
