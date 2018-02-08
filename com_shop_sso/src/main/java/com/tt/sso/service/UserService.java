package com.tt.sso.service;

import com.tt.pojo.TbUser;
import com.tt.util.TaotaoResult;

public interface UserService {

    TaotaoResult checkData(String content,Integer type);

    TaotaoResult createUser(TbUser tbUser);

    TaotaoResult userLogin(String username,String password);

    TaotaoResult getUserByToken(String token);
}
