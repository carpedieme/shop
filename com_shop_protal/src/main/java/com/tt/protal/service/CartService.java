package com.tt.protal.service;

import com.tt.protal.pojo.CartItem;
import com.tt.util.TaotaoResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface CartService {

    TaotaoResult addCartItem(long itemId, int num, HttpServletRequest request, HttpServletResponse response);

    List<CartItem> getCartItemList(HttpServletRequest request, HttpServletResponse response);

    TaotaoResult delCartItem(long itemId,HttpServletRequest request,HttpServletResponse response);
}
