package com.tt.protal.service.com.tt.protal.service.impl;

import com.tt.protal.pojo.SearchResult;
import com.tt.protal.service.SearchService;
import com.tt.protal.util.HttpClientUtil;
import com.tt.util.TaotaoResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 商品搜索service
 *
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Value("${solr_base_url}")
    private String solr_base_url;

    @Override
    public SearchResult search(String queryStr, int page) {

        //调用search的服务
        //查询参数
        Map<String, String> map = new HashMap<>();
        map.put("q", queryStr);
        map.put("page", page + "");

        try {
            //调用search服务
            String json = HttpClientUtil.doGet(solr_base_url, map);
            //将字符串转换为java对象
            TaotaoResult result = TaotaoResult.formatToPojo(json, SearchResult.class);
            if (result.getStatus() == 200) {
                SearchResult searchresult = (SearchResult) result.getData();
                return searchresult;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
