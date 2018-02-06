package com.tt.protal.service;

import com.tt.protal.pojo.ItemInfo;

public interface ItemService {

    ItemInfo getItemById(Long id);

    String getItemDesc(Long itemId);

    String getItamParam(Long itemId);
}
