package com.tt.service;

import com.tt.pojo.TbItem;
import com.tt.util.EasyUIDateGrid;
import com.tt.util.TaotaoResult;

public interface TbItemService {

    /**
     * 测试查询
     * @param itemid
     * @return
     */
    TbItem getItremById(Long itemid);
    //返回一个EasyUIDateGrid类型的json对象
    EasyUIDateGrid getItemList(int page,int rows);
    //添加商品以及商品描述
    TaotaoResult createItem(TbItem item,String descm,String itemParams) throws Exception;
    /**
     *
     * @param itemId
     * @param itamParam
     * @return
     */
    TaotaoResult insertTbItemparamItem(long itemId,String itamParam);
}
