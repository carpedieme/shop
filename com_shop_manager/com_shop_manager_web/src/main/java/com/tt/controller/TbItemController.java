package com.tt.controller;


import com.tt.pojo.TbItem;
import com.tt.service.TbItemService;
import com.tt.util.EasyUIDateGrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TbItemController {

    @Autowired
    private TbItemService tbItemService;

    @RequestMapping("/item/{itemId}")
    @ResponseBody
    public TbItem getItemById(@PathVariable Long itemId){

        return tbItemService.getItremById(itemId);
    }

    @RequestMapping("/item/list")
    @ResponseBody
    public EasyUIDateGrid getItemList(Integer page,Integer rows){

        EasyUIDateGrid eug=tbItemService.getItemList(page,rows);
        return  eug;
    }

}
