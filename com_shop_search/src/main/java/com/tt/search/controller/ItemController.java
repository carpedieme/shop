package com.tt.search.controller;

import com.tt.search.service.ItemService;
import com.tt.util.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/manager")
public class ItemController {

    @Autowired
    private ItemService itemService;

    /**
     * 导入商品数据到索引库
     *
     * @return
     */
    @RequestMapping("/importall")
    @ResponseBody
    public TaotaoResult getItemLiat() {
        return itemService.importAllItems();
    }


}
