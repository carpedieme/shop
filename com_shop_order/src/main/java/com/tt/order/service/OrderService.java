package com.tt.order.service;

import com.tt.pojo.TbOrder;
import com.tt.pojo.TbOrderItem;
import com.tt.pojo.TbOrderShipping;
import com.tt.util.TaotaoResult;

import java.util.List;

public interface OrderService {

    TaotaoResult createOrder(TbOrder order, List<TbOrderItem> orderItemList, TbOrderShipping orderShipping);
}
