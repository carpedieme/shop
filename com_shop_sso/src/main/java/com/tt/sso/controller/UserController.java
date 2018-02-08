package com.tt.sso.controller;

import com.tt.pojo.TbUser;
import com.tt.sso.service.UserService;
import com.tt.util.TaotaoResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author sjg
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 校验数据
     *
     * @param param
     * @param type
     * @param callback
     * @return
     */
    @RequestMapping("/check/{param}/{type}")
    @ResponseBody
    public Object checkData(@PathVariable String param, @PathVariable Integer type, String callback) {

        TaotaoResult result = null;
        //参数有效性校验
        if (StringUtils.isBlank(param)) {
            result = TaotaoResult.build(400, "校验内容不能为空");
        }
        if (type == null) {
            result = TaotaoResult.build(400, "校验内容类型不能为空");
        }
        if (type != 1 && type != 2 && type != 3) {
            result = TaotaoResult.build(400, "校验内容类型有误");
        }
        //校验出错
        if (null != result) {
            if (null != callback) {
                MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
                mappingJacksonValue.setJsonpFunction(callback);
                return mappingJacksonValue;
            } else {
                return result;
            }
        }
        //调用服务
        try {
            result = userService.checkData(param, type);
            return result;
        } catch (Exception e) {
            result = TaotaoResult.build(500, "校验出错了。。。" + e);
        }

        if (null != callback) {
            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
            mappingJacksonValue.setJsonpFunction(callback);
            return mappingJacksonValue;
        } else {
            return result;
        }
    }

    /**
     * 用户注册
     *
     * @param tbUser
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult createUser(TbUser tbUser) {

        try {
            TaotaoResult result = userService.createUser(tbUser);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, "用户注册失败。。。" + e);
        }


    }

    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @return
     */

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult userLogin(String username, String password) {

        try {
            TaotaoResult result = userService.userLogin(username, password);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, "用户登录失败。。。" + e);
        }
    }

    @RequestMapping("/token/{token}")
    @ResponseBody
    public Object getUserByToken(@PathVariable String token, String callback) {

        TaotaoResult result = null;

        try {
            result = userService.getUserByToken(token);
        } catch (Exception e) {
            e.printStackTrace();
            result = TaotaoResult.build(400, "获取用户token失败" + e);
        }

        //判断是否为jsonp调用
        if (StringUtils.isBlank(callback)) {
            return result;
        } else {
            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
            mappingJacksonValue.setJsonpFunction(callback);
            return mappingJacksonValue;
        }
    }

}
