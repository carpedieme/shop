package com.tt.controller;

import com.tt.service.TbItemParamItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TbItemParamItemController {

    @Autowired
    private TbItemParamItemService paramItemService;

    @RequestMapping("/showitem/{itemId}")
    @ResponseBody
    public String showItemParam(@PathVariable Long itemId, Model model){

        String str=paramItemService.GetTbItemParamItemByitemId(itemId);
        model.addAttribute("itemParam",str);
        return "item";
    }
}
