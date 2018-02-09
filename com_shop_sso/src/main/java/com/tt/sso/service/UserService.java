package com.tt.sso.service;

import com.tt.pojo.TbUser;
import com.tt.util.TaotaoResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserService {

    TaotaoResult checkData(String content,Integer type);

    TaotaoResult createUser(TbUser tbUser);

    TaotaoResult userLogin(String username, String password, HttpServletRequest request, HttpServletResponse response);

    TaotaoResult getUserByToken(String token);

    TaotaoResult userLoginOut(String token);
}
