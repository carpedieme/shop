package com.tt.protal.service;

import com.tt.pojo.TbUser;

public interface UserService {

    TbUser getUserByToken(String token);
}
