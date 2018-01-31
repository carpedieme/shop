package com.tt.rest.service;

import com.tt.rest.dao.JedisClient;
import com.tt.util.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private JedisClient jedisClient;

    @Value("${index_content_redis_key}")
    private String index_content_redis_key;

    @Override
    public TaotaoResult syncContent(long contentCid) {
        try {
            jedisClient.hdel(index_content_redis_key, contentCid + "");
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, "缓存清除失败。。。");
        }
        return TaotaoResult.ok();
    }
}
