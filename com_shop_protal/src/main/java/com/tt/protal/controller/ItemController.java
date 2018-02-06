package com.tt.protal.controller;

import com.tt.protal.pojo.ItemInfo;
import com.tt.protal.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    /**
     * 展示商品基本信息
     * @param itemId
     * @param model
     * @return
     */
    @RequestMapping("/item/{itemId}")
    public String showItem(@PathVariable Long itemId, Model model) {
        ItemInfo item = itemService.getItemById(itemId);
        model.addAttribute("item", item);
        return "item";
    }

    /**
     * 展示商品描述信息
     * 并进行乱码处理
     * @param itemId
     * @return
     */
    @RequestMapping(value = "/item/desc/{itemId}",produces = MediaType.TEXT_HTML_VALUE+";charset=utf-8")
    @ResponseBody
    public String showItemDesc(@PathVariable Long itemId) {
        return itemService.getItemDesc(itemId);
    }

    /**
     * 展示商品规格参数信息
     * 并进行乱码处理
     * @param itemId
     * @return
     */
    @RequestMapping(value = "/item/param/{itemId}",produces = MediaType.TEXT_HTML_VALUE+";charset=utf-8")
    @ResponseBody
    public String showItemParam(@PathVariable Long itemId) {
        return itemService.getItamParam(itemId);
    }
}
