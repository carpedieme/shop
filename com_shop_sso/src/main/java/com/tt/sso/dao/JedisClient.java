package com.tt.sso.dao;

public interface JedisClient {

    String get(String key);
    String set(String key, String value);
    //通过hash取值
    String hget(String hkey, String key);
    Long hset(String hkey, String key, String value);
    long incr(String key);
    //设置其有效期
    long expire(String key, int second);
    long ttl(String key);
    //删除
    long del(String key);
    //删除hash中的key
    long hdel(String hkey, String key);
}
