package com.tt.sso.service.impl;

import com.tt.mapper.TbUserMapper;
import com.tt.pojo.TbUser;
import com.tt.pojo.TbUserExample;
import com.tt.sso.CookieUtils;
import com.tt.sso.dao.JedisClient;
import com.tt.sso.service.UserService;
import com.tt.util.JsonUtils;
import com.tt.util.TaotaoResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author sjg
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private TbUserMapper userMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${redis_user_session_key}")
    private String redis_user_session_key;

    @Value("${sso_session_expire}")
    private Integer sso_session_expire;

    /**
     * 校验数据
     *
     * @param content
     * @param type
     * @return
     */
    @Override
    public TaotaoResult checkData(String content, Integer type) {

        //创建查询条件
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria cr = example.createCriteria();
        //对数据进行校验：1,2,3分别代表username,phone,email
        if (type == 1) {
            cr.andUsernameEqualTo(content);
        } else if (type == 2) {
            cr.andPhoneEqualTo(content);

        } else {
            cr.andEmailEqualTo(content);
        }
        List<TbUser> us = userMapper.selectByExample(example);
        if (us != null && us.size() > 0) {
            return TaotaoResult.ok(false);
        }
        return TaotaoResult.ok(true);
    }

    /**
     * 用户注册
     *
     * @param tbUser
     * @return
     */
    @Override
    public TaotaoResult createUser(TbUser tbUser) {

        tbUser.setUpdated(new Date());
        tbUser.setCreated(new Date());
        //md5加密
        tbUser.setPassword(DigestUtils.md5DigestAsHex(tbUser.getPassword().getBytes()));
        userMapper.insert(tbUser);
        return TaotaoResult.ok();
    }

    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public TaotaoResult userLogin(String username, String password, HttpServletRequest request, HttpServletResponse response) {

        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria cr = example.createCriteria();
        cr.andUsernameEqualTo(username);
        List<TbUser> list = userMapper.selectByExample(example);
        if (list == null && list.size() == 0) {
            return TaotaoResult.build(400, "用户名或密码错误");
        }
        TbUser user = list.get(0);
        //校验密码
        if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())) {
            return TaotaoResult.build(400, "用户名或密码错误");
        }
        //生成token
        String token = UUID.randomUUID().toString();
        //保存用户信息之前，吧用户密码清空
        user.setPassword(null);
        //将用户信息放入缓存
        jedisClient.set(redis_user_session_key + ":" + token, JsonUtils.objectToJson(user));
        //设置session过期时间
        jedisClient.expire(redis_user_session_key + ":" + token, sso_session_expire);
        //添加写cookie的逻辑，cookie的有效期是关闭浏览器就失效
        CookieUtils.setCookie(request,response,"TT_TOKEN",token);
        //返回token
        return TaotaoResult.ok(token);
    }

    /**
     * 通过token来获取缓存中存放的用户的信息
     *
     * @param token
     * @return
     */
    @Override
    public TaotaoResult getUserByToken(String token) {

        String json = jedisClient.get(redis_user_session_key + ":" + token);
        //判断拿到的数据是否为空
        if(StringUtils.isBlank(json)){
            return  TaotaoResult.build(400,"用户登录超时，请重新登录");
        }
        //更新过期时间
        jedisClient.expire(redis_user_session_key + ":" + token,sso_session_expire);
        //需要转换为json对象，否则在浏览器获取的时候拿到的数据不满足要求。
        return TaotaoResult.ok(JsonUtils.jsonToPojo(json,TbUser.class));
    }

    /**
     * 通过token来操作用户退出登录
     * @param token
     * @return
     */
    @Override
    public TaotaoResult userLoginOut(String token) {

        String json=jedisClient.get(redis_user_session_key + ":" + token);
        if(json!=null){
            //有值
            long result = jedisClient.del(redis_user_session_key + ":" + token);
            if(result>0){
                return TaotaoResult.ok();

            }
        }
        return TaotaoResult.build(400,"退出登录失败...");
    }
}
