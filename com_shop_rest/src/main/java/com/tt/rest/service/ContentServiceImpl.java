package com.tt.rest.service;

import com.tt.mapper.TbContentMapper;
import com.tt.pojo.TbContent;
import com.tt.pojo.TbContentExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 内容管理
 */
@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentMapper contentMapper;
    /**
     * 接收内容分类id，根据分类id查询分类列表。返回一个内容pojo列表。
     * @param contentCid
     * @return
     */
    @Override
    public List<TbContent> getContentList(long contentCid) {
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria cr = example.createCriteria();
        cr.andCategoryIdEqualTo(contentCid);
        List<TbContent> list = contentMapper.selectByExample(example);
        return list;
    }
}
