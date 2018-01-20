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
    //添加商品
    TaotaoResult createItem(TbItem item);
}
