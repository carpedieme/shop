package com.tt.controller;


import com.tt.service.TbItemCatService;
import com.tt.util.EasyTreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 新增商品列表controller类
 */
@Controller
@RequestMapping("/item/cat")
public class ItemCatController {

    @Autowired
    private TbItemCatService tbItemCatService;

    @RequestMapping("/list")
    @ResponseBody
    public List<EasyTreeNode> getTreeNode(@RequestParam(value = "id",defaultValue = "0")Long parentId){
       return tbItemCatService.getTbitemList(parentId);

    }
}
