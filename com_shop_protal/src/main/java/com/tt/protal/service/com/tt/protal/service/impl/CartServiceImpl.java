package com.tt.protal.service.com.tt.protal.service.impl;

import com.tt.pojo.TbItem;
import com.tt.protal.pojo.CartItem;
import com.tt.protal.service.CartService;
import com.tt.protal.util.CookieUtils;
import com.tt.protal.util.HttpClientUtil;
import com.tt.util.JsonUtils;
import com.tt.util.TaotaoResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sjg
 */
@Service
public class CartServiceImpl implements CartService {

    //调用rest服务
    @Value("${rest_baseurl}")
    private String rest_baseurl;

    @Value("${item_base_url}")
    private String item_base_url;

    /**
     * 添加购物车
     *
     * @param itemId
     * @param num
     * @return
     */
    @Override
    public TaotaoResult addCartItem(long itemId, int num, HttpServletRequest request, HttpServletResponse response) {
        //取商品信息
        CartItem cartItem = null;
        //获取购物车的商品列表
        List<CartItem> listCart = getCartItemList(request);
        for (CartItem item : listCart) {
            //判断是否存在此商品
            if (item.getId() == itemId) {
                //增加商品数量
                item.setNum(item.getNum() + num);
                cartItem = item;
                break;
            }
        }
        //如果购物车商品列表中没有此商品 重新创建一个并将添加商品信息
        if (cartItem == null) {
            cartItem = new CartItem();
            //通过HttpClientUtil调用rest服务查询商品信息
            String json = HttpClientUtil.doGet( rest_baseurl +item_base_url+ itemId);
            if (json != null) {
                //吧json转换为java对象
                TaotaoResult result = TaotaoResult.formatToPojo(json, TbItem.class);
                if (result.getStatus() == 200) {
                    TbItem tbItem = (TbItem) result.getData();
                    cartItem.setId(tbItem.getId());
                    cartItem.setTitle(tbItem.getTitle());
                    cartItem.setNum(num);
                    cartItem.setPrice(tbItem.getPrice());
                    cartItem.setImage(tbItem.getImage() == null ? "" : tbItem.getImage().split(",")[0]);
                }
                //在购物车列表中添加此商品
                listCart.add(cartItem);
            }
            //把购物车列表写入cookie
            CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(listCart), true);
        }
        return TaotaoResult.ok();
    }

    /**
     * 从cookie中把购物车列表取出来，没有参数，返回购物车中的商品列表。
     *
     * @param request
     * @param response
     * @return
     */
    @Override
    public List<CartItem> getCartItemList(HttpServletRequest request, HttpServletResponse response) {
        List<CartItem> list = getCartItemList(request);
        return list;
    }

    /**
     * 从购物车中删除商品
     *
     * @param itemId
     * @param request
     * @param response
     * @return
     */
    @Override
    public TaotaoResult delCartItem(long itemId, HttpServletRequest request, HttpServletResponse response) {

        //从cookie中取购物车信息
        List<CartItem> list = getCartItemList(request);
        for (CartItem item : list) {
          //从商品列表中找到此商品
            if (item.getId()==itemId) {

                list.remove(item);
                break;
            }
        }
        //把购物车重新写入cookie
        CookieUtils.setCookie(request,response,"TT_CART",JsonUtils.objectToJson(list),true);

        return TaotaoResult.ok();
    }

    /**
     * 从cookie中取商品列表
     *
     * @return
     */
    private List<CartItem> getCartItemList(HttpServletRequest request) {

        //cookie中获取购物车列表
        String json = CookieUtils.getCookieValue(request, "TT_CART", true);
        if (json == null) {
            return new ArrayList<>();
        }
        try {
            //吧json转换为商品列表
            List<CartItem> list = JsonUtils.jsonToList(json, CartItem.class);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
