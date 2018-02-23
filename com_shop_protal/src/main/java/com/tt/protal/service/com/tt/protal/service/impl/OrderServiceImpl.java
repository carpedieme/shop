package com.tt.protal.service.com.tt.protal.service.impl;

import com.tt.protal.pojo.Order;
import com.tt.protal.service.OrderService;
import com.tt.protal.util.HttpClientUtil;
import com.tt.util.JsonUtils;
import com.tt.util.TaotaoResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author sjg
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Value("${order_base_url}")
    private String order_base_url;

    @Value("${order_create_url}")
    private String order_create_url;

    @Override
    public String createOrder(Order order) {
        //调用taotao-order的服务提交订单
        String json = HttpClientUtil.doPostJson(order_base_url + order_create_url, JsonUtils.objectToJson(order));
        //吧json装换为TaoTaoResult
        TaotaoResult result = TaotaoResult.format(json);
        if (result.getStatus() == 200) {
            Object orderId =  result.getData();
            return orderId.toString();
        }
        return "";
    }
}
