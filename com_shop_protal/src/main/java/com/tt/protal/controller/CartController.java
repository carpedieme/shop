package com.tt.protal.controller;

import com.tt.protal.pojo.CartItem;
import com.tt.protal.service.CartService;
import com.tt.util.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author sjg
 * 购物车controller
 */
@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    /**
     * 添加家商品到购物车
     *
     * @param itemId
     * @param num
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/add/{itemId}")
    public String addItemCart(@PathVariable Long itemId, @RequestParam(defaultValue = "1") Integer num,
                              HttpServletRequest request, HttpServletResponse response) {

        TaotaoResult result = cartService.addCartItem(itemId, num, request, response);
        return "redirect:/cart/success.html";
    }

    @RequestMapping("/success")
    public String showSuccess() {
        return "cartSuccess";
    }

    /**
     * 展示购物车信息
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/cart")
    public String showCart(HttpServletRequest request, HttpServletResponse response, Model model) {
        List<CartItem> list = cartService.getCartItemList(request, response);
        model.addAttribute("cartList", list);
        return "cart";

    }

    /**
     * 从购物车中删除商品
     *
     * @param itemId
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/delete/{itemId}")
    public String deleteItem(@PathVariable Long itemId, HttpServletRequest request, HttpServletResponse response) {
        cartService.delCartItem(itemId, request, response);
        return "redirect:/cart/cart.html";
    }


}
