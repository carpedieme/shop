package com.tt.rest.service.impl;

import com.tt.mapper.TbItemDescMapper;
import com.tt.mapper.TbItemMapper;
import com.tt.mapper.TbItemParamItemMapper;
import com.tt.mapper.TbItemParamMapper;
import com.tt.pojo.TbItem;
import com.tt.pojo.TbItemDesc;
import com.tt.pojo.TbItemParamItem;
import com.tt.pojo.TbItemParamItemExample;
import com.tt.rest.dao.JedisClient;
import com.tt.rest.service.ItemService;
import com.tt.util.JsonUtils;
import com.tt.util.TaotaoResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper tbItemMapper;

    @Autowired
    private TbItemDescMapper itemDescMapper;

    @Autowired
    private TbItemParamItemMapper tbItemParamItemMapper;

    //调用redis客户端
    @Autowired
    private JedisClient jedisClient;
    //读取属性文件
    @Value("${redis_item_key}")
    private String redis_item_key;

    @Value("${redis_time_expire}")
    private Integer redis_time_expire;
    /**
     * 获取商品基本信息
     * @param id
     * @return
     */
    @Override
    public TaotaoResult getItemBaseInfo(long id) {
        //从缓存中取
        try {
            String json = jedisClient.get(redis_item_key + ":" + id + ":base");
            if(!StringUtils.isBlank(json)){
               TbItem item=(TbItem) JsonUtils.jsonToPojo(json,TbItem.class);
                return TaotaoResult.ok(item);
            }
        }catch (Exception e){
            e.printStackTrace();

        }
        //根据商品查询商品
        TbItem tbItem = tbItemMapper.selectByPrimaryKey(id);
        //放入缓存
        try {
            jedisClient.set(redis_item_key+":"+id+":base", JsonUtils.objectToJson(tbItem));
        //设置key的有效期
        jedisClient.expire(redis_item_key+":"+id+":base",redis_time_expire);
        }catch (Exception e){
         e.printStackTrace();
        }
        return TaotaoResult.ok(tbItem);
    }

    /**
     * 获取商品描述信息
     * @param id
     * @return
     */
    @Override
    public TaotaoResult getItemDesc(long id) {

        //从缓存中获取描述信息
            try {
                String json = jedisClient.get(redis_item_key + ":" + id + ":desc");
                if(!StringUtils.isBlank(json)){
                    TbItemDesc desc=JsonUtils.jsonToPojo(json,TbItemDesc.class);
                    return TaotaoResult.ok(desc);
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        TbItemDesc result = itemDescMapper.selectByPrimaryKey(id);
        //将描述信息放入缓存
        try {
         jedisClient.set(redis_item_key+":"+id+":desc",JsonUtils.objectToJson(result));
       //设置有效期 一天
            jedisClient.expire(redis_item_key+":"+id+":desc",redis_time_expire);
        }catch (Exception e){
            e.printStackTrace();
        }
        return TaotaoResult.ok(result);
    }

    /**
     * 获取商品规格参数
     * @param id
     * @return
     */
    @Override
    public TaotaoResult getItemParam(long id) {

        //从缓存取
        try {
            String json = jedisClient.get(redis_item_key + ":" + id + ":param");
            if(!StringUtils.isBlank(json)){
                TbItemParamItem item = JsonUtils.jsonToPojo(json, TbItemParamItem.class);
                return TaotaoResult.ok(item);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        TbItemParamItemExample example=new TbItemParamItemExample();
        TbItemParamItemExample.Criteria cri = example.createCriteria();
        cri.andItemIdEqualTo(id);
        List<TbItemParamItem> list = tbItemParamItemMapper.selectByExampleWithBLOBs(example);
        if(list!=null&&list.size()>0){
            TbItemParamItem paramitem = list.get(0);
            //添加缓存
            try {
                jedisClient.set(redis_item_key+":"+id+":param",JsonUtils.objectToJson(paramitem));
                //设置缓存时间
                jedisClient.expire(redis_item_key+":"+id+":param",redis_time_expire);
            }catch (Exception e){
              e.printStackTrace();
            }
            return  TaotaoResult.ok(paramitem);
        }
        return TaotaoResult.build(400,"无此商品规格");
    }
}
