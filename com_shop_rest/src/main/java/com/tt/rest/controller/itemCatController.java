package com.tt.rest.controller;

import com.tt.rest.pojo.CatResult;
import com.tt.rest.service.ItemCatService;
import com.tt.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class itemCatController {

    @Autowired
    private ItemCatService itemCatService;
    //方法一
//produces= MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8"解决乱码
//    @RequestMapping(value = "/itemcat/list",produces= MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
//    @ResponseBody
//    public String getItemCatList(String callback) {
//        CatResult catResult = itemCatService.getCatItemList();
//        //把pojo转换成字符串
//        String json = JsonUtils.objectToJson(catResult);
//        //拼装返回值
//        String result = callback + "(" + json + ");";
//        return result;
//    }


//方法二  版本是spring4.1以后才能用
    @RequestMapping("/itemcat/list")
    @ResponseBody
    public Object getItemCatList(String callback) {
        CatResult catResult = itemCatService.getCatItemList();
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(catResult);
        mappingJacksonValue.setJsonpFunction(callback);
        return mappingJacksonValue;
    }



}
