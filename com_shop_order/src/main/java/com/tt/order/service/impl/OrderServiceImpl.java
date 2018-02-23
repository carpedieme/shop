package com.tt.order.service.impl;

import com.tt.mapper.TbOrderItemMapper;
import com.tt.mapper.TbOrderMapper;
import com.tt.mapper.TbOrderShippingMapper;
import com.tt.order.dao.JedisClient;
import com.tt.order.service.OrderService;
import com.tt.pojo.TbOrder;
import com.tt.pojo.TbOrderItem;
import com.tt.pojo.TbOrderShipping;
import com.tt.util.TaotaoResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 订单操作
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private TbOrderMapper orderMapper;

    @Autowired
    private TbOrderItemMapper orderItemMapper;

    @Autowired
    private TbOrderShippingMapper orderShippingMapper;

    @Autowired
    private JedisClient jedisClient;
    @Value("${order_gen_key}")
    private String order_gen_key;
    @Value("order_init_id")
    private String order_init_id;
    @Value("${order_detail_gen_key}")
    private String order_detail_gen_key;

    /**
     * 创建订单
     * @param order
     * @param orderItemList
     * @param orderShipping
     * @return
     */
    @Override
    public TaotaoResult createOrder(TbOrder order, List<TbOrderItem> orderItemList, TbOrderShipping orderShipping) {

        //向订单表中插入记录
        //首先获取订单号
        String str = jedisClient.get(order_gen_key);
        if (StringUtils.isBlank(str)) {
            jedisClient.set(order_gen_key, order_init_id);
        }
        long orderId = jedisClient.incr(order_gen_key);
        //补全pojo属性
        order.setOrderId(orderId + "");
        //状态：1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭
        order.setStatus(1);
        Date date = new Date();
        order.setCreateTime(date);
        order.setUpdateTime(date);
        //卖家0未评价  1已评价
        order.setBuyerRate(0);
        //向订单表中插入数据
        orderMapper.insert(order);
        //插入订单明细
        for (TbOrderItem item : orderItemList) {
            //补全订单明细
            //获取订单明细id
            long orderDetailId = jedisClient.incr(order_detail_gen_key);
            item.setId(orderDetailId + "");
            item.setOrderId(orderId+"");
            //想订单明细中插入记录
            orderItemMapper.insert(item);
        }
        //项插入物流表
        orderShipping.setCreated(date);
        orderShipping.setUpdated(date);
        orderShipping.setOrderId(orderId+"");
        orderShippingMapper.insert(orderShipping);
        return TaotaoResult.ok(orderId);
    }
}
