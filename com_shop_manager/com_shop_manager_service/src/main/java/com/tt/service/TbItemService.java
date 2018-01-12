package com.tt.service;

import com.tt.pojo.TbItem;
import com.tt.util.EasyUIDateGrid;

public interface TbItemService {

    TbItem getItremById(Long itemid);
//返回一个EasyUIDateGrid类型的json对象
    EasyUIDateGrid getItemList(int page,int rows);
}
