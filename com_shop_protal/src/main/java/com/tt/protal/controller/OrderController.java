package com.tt.protal.controller;

import com.tt.protal.pojo.CartItem;
import com.tt.protal.pojo.Order;
import com.tt.protal.service.CartService;
import com.tt.protal.service.OrderService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * @author sjg
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    @RequestMapping("/create")
    public String createOrder(Order order,Model model){


        try {
            String orderId = orderService.createOrder(order);
            model.addAttribute("orderId",orderId);
            model.addAttribute("payment",order.getPayment());
            model.addAttribute("date",new DateTime().plusDays(3).toString("yyyy-MM-dd"));
            return  "success";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message","创建订单出错"+e);
            return "error/exception";
        }

    }


    /**
     * 订单确认
     * @return
     */
    @RequestMapping("/order-cart")
    public String showCartOrder(HttpServletRequest request, HttpServletResponse response, Model model) {
        //获取购物车列表
        List<CartItem> list = cartService.getCartItemList(request, response);
        //传递给页面
        model.addAttribute("cartList",list);
        return "order-cart";

    }
}
