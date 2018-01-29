package com.tt.protal.service.com.tt.protal.service.impl;

import com.tt.pojo.TbContent;
import com.tt.protal.service.ContentService;
import com.tt.protal.util.HttpClientUtil;
import com.tt.util.JsonUtils;
import com.tt.util.TaotaoResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 调用服务层服务，查询内容列表
 */
@Service
public class ContentServiceImpl implements ContentService {

    @Value("${rest_baseurl}")
    private String rest_baseurl;

    @Value("${rest_indexurl}")
    private String rest_indexurl;

    /**
     * 根据内容分类id查询分类的内容列表，需要使用httpclient调用taotao-rest的服务。得到一个json字符串。
     * 需要把字符串转换成java对象taotaoResult对象。
     * 从taotaoResult对象中取data属性，得到内容列表。把内容列表转换成jsp页面要求的json格式。返回一个json字符串。
     * @return
     */
    @Override
    public String getContentList() {
        //调用服务层服务
        String str = HttpClientUtil.doGet(rest_baseurl + rest_indexurl);
        //把字符串转换为TaotaoResult
        try {
            TaotaoResult taotaoResult = TaotaoResult.formatToList(str, TbContent.class);
            //取出内容列表
            List<TbContent> list = (List<TbContent>) taotaoResult.getData();
            List<Map> resultList = new ArrayList<>();
            //创建一个jsp页码要求的pojo列表
            for (TbContent content : list) {
                Map map = new HashMap();
                map.put("src", content.getPic());
                map.put("width", 670);
                map.put("height", 240);
                map.put("srcB", content.getPic2());
                map.put("widthB", 550);
                map.put("heightB", 240);
                map.put("href", content.getUrl());
                map.put("alt", content.getSubTitle());
                resultList.add(map);
            }
            return JsonUtils.objectToJson(resultList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
