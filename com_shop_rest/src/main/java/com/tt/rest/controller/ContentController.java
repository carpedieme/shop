package com.tt.rest.controller;

import com.tt.rest.service.ContentService;
import com.tt.util.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/content")
public class ContentController {

    @Autowired
    private ContentService contentService;


    /**
     * 发布服务。接收查询参数。Restful风格内容分类id应该从url中取。
     * 自己定义：/rest/content/list/{contentCategoryId}
     * 从url中取内容分类id，调用Service查询内容列表。返回内容列表。返回一个json格式的数据。可以使用TaotaoResult包装此列表。
     *
     * @param contentCategoryId
     * @return
     */
    @RequestMapping("/list/{contentCategoryId}")
    @ResponseBody
    public TaotaoResult getContentList(@PathVariable Long contentCategoryId) {
        //没有做异常处理 所以才需要加入异常处理
        try {
            return TaotaoResult.ok(contentService.getContentList(contentCategoryId));
        } catch (Exception e) {
            e.printStackTrace();
            //在common中加入异常工具类
            return TaotaoResult.build(500,"发生异常" );
        }
    }


}
