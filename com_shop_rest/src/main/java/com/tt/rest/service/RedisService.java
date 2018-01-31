package com.tt.rest.service;

import com.tt.util.TaotaoResult;

public interface RedisService {

    TaotaoResult syncContent(long contentCid);
}
