package com.tt.service.tt.serviceImpl;

import com.tt.mapper.TbContentMapper;
import com.tt.pojo.TbContent;
import com.tt.service.ContentService;
import com.tt.util.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentMapper contentMapper;

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
        return TaotaoResult.ok();
    }
}
