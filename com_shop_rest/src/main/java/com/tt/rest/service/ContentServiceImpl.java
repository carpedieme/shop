package com.tt.rest.service;

import com.tt.mapper.TbContentMapper;
import com.tt.pojo.TbContent;
import com.tt.pojo.TbContentExample;
import com.tt.rest.dao.JedisClient;
import com.tt.util.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 内容管理
 */
@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentMapper contentMapper;
    //调用缓存工具类
    @Autowired
    private JedisClient jedisClient;

    @Value("${index_content_redis_key}")
    private String index_content_redis_key;

    /**
     * 此处需要添加缓存
     * 接收内容分类id，根据分类id查询分类列表。返回一个内容pojo列表。
     * @param contentCid
     * @return
     */
    @Override
    public List<TbContent> getContentList(long contentCid) {
        //从缓存中取
        try {
            String result = jedisClient.hget(index_content_redis_key, contentCid + "");
            if (!StringUtils.isBlank(result)) {
                //把字符串转换为list
                List<TbContent> listContent = JsonUtils.jsonToList(result, TbContent.class);
                return listContent;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria cr = example.createCriteria();
        cr.andCategoryIdEqualTo(contentCid);
        List<TbContent> list = contentMapper.selectByExample(example);

        //从缓存中添加
        try {
            //缓存中放的是String字符串，先把list对象转化为字符串
            String cacheList = JsonUtils.objectToJson(list);
            //将其存放到缓存中 使用hash
            jedisClient.hset(index_content_redis_key, contentCid + "", cacheList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
