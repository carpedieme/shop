package com.tt.rest.controller;

import com.tt.rest.service.ItemService;
import com.tt.util.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 根據id查詢商品信息
 */
@Controller
@RequestMapping("item")
public class ItemController {
    @Autowired
    private ItemService itemService;

    /**
     * 获取商品基本信息
     * @param itemId
     * @return
     */
    @RequestMapping("/info/{itemId}")
    @ResponseBody
    public TaotaoResult getItemBaseInfo(@PathVariable Long itemId){

        return itemService.getItemBaseInfo(itemId);
    }

    /**
     * 获取商品描述信息
     * @param itemId
     * @return
     */
    @RequestMapping("/desc/{itemId}")
    @ResponseBody
    public TaotaoResult getItemDesc(@PathVariable Long itemId){
        return itemService.getItemDesc(itemId);
    }

    /**
     * 获取商品规格参数
     * @param itemId
     * @return
     */
    @RequestMapping("/param/{itemId}")
    @ResponseBody
    public TaotaoResult getItemParam(@PathVariable Long itemId){
        return itemService.getItemParam(itemId);
    }




}
