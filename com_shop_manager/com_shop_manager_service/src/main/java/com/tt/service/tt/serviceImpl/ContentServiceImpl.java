package com.tt.service.tt.serviceImpl;

import com.tt.mapper.TbContentMapper;
import com.tt.pojo.TbContent;
import com.tt.service.ContentService;
import com.tt.util.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import utils.HttpClientUtil;

import java.util.Date;

@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentMapper contentMapper;

    @Value("${rest_base_url}")
    private String rest_base_url;

    @Value("${rest_content_url}")
    private String rest_content_url;

    /**
     * 接收表tb_content对应的pojo对象。把pojo对象插入到tb_content表中。
     * @param content
     * @return
     */
    @Override
    public TaotaoResult insertContent(TbContent content) {
        //补全pojo内容
        content.setCreated(new Date());
        content.setUpdated(new Date());
        contentMapper.insert(content);
        //同步添加缓存
        try {
            HttpClientUtil.doGet(rest_base_url + rest_content_url + content.getCategoryId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return TaotaoResult.ok();
    }
}
