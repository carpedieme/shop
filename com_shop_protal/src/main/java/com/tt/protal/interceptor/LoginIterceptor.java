package com.tt.protal.interceptor;

import com.tt.pojo.TbUser;
import com.tt.protal.service.UserService;
import com.tt.protal.service.com.tt.protal.service.impl.UserServiceImpl;
import com.tt.protal.util.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 创建拦截器
 */
public class LoginIterceptor implements HandlerInterceptor {

    @Autowired
    private UserServiceImpl userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //在handler执行之前   返回值true表示执行   false表示不执行
        //判断用户是否登录
        //从cookie中获取token
        String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
        //根据token换取用户信息 调用sso系统的服务
        TbUser user = userService.getUserByToken(token);
        //取不到信息
        if (user == null) {
            //跳转到登录页面  把用户请求的url作为参数传递给页面
            response.sendRedirect(userService.sso_base_url + userService.sso_user_login + "?redirect=" + request.getRequestURL());
        //返回false
            return false;
        }
        //获取到用户信息 就放行
        return  true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //在handler执行之后，返回modelAndView之前
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //在handler执行之后   响应完以后
    }
}
