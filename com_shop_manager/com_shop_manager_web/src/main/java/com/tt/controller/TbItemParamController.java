package com.tt.controller;

import com.tt.pojo.TbItemParam;
import com.tt.service.TbItemParamService;
import com.tt.util.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/item/param")
public class TbItemParamController {

    @Autowired
    private TbItemParamService paramService;


    @RequestMapping("/query/itemcatid/{itemCatId}")
    @ResponseBody
    public TaotaoResult getTbItemParam(@PathVariable Long itemCatId){

        return paramService.getTbItemParam(itemCatId);


    }

    @RequestMapping("/save/{cid}")
    @ResponseBody
    public TaotaoResult insertTbItemParam(@PathVariable Long cid,String paramData){

        //创建pojo对象
        TbItemParam param =new TbItemParam();
        param.setItemCatId(cid);
        param.setParamData(paramData);
        return  paramService.insertTbItemParam(param);
    }

}
