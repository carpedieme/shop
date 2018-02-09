package com.tt.protal.service.com.tt.protal.service.impl;

import com.tt.pojo.TbUser;
import com.tt.protal.service.UserService;
import com.tt.protal.util.HttpClientUtil;
import com.tt.util.TaotaoResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author sjg
 *
 * 根据token获取用户信息
 */

@Service
public class UserServiceImpl implements UserService {

    @Value("${sso_base_url}")
    public String sso_base_url;

    @Value("${sso_user_token}")
    private String sso_user_token;

    @Value("${sso_user_login}")
    public String sso_user_login;

    @Override
    public TbUser getUserByToken(String token) {
        try {
            //调用sso系统服务，根据token获取用户信息
            String json = HttpClientUtil.doGet(sso_base_url + sso_user_token + token);
            if (json != null) {
                TaotaoResult result = TaotaoResult.formatToPojo(json, TbUser.class);
                if (result.getStatus() == 200) {
                    TbUser user = (TbUser) result.getData();
                    return user;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
