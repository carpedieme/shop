package com.tt.rest.service.impl;

import com.tt.mapper.TbItemCatMapper;
import com.tt.pojo.TbItemCat;
import com.tt.pojo.TbItemCatExample;
import com.tt.rest.dao.JedisClient;
import com.tt.rest.pojo.CatNode;
import com.tt.rest.pojo.CatResult;
import com.tt.rest.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private TbItemCatMapper itemCatMapper;
    //调用工具类
    @Autowired
    private JedisClient jedisClient;

    @Value("${index_content_redis_key}")
    private String index_content_redis_key;

    @Override
    public CatResult getCatItemList() {

        CatResult catResult = new CatResult();
        //查询分类列表
        catResult.setData(getCatList(0));
        return catResult;
    }
    /**
     * 查询分类列表
     * <p>Title: getCatList</p>
     * <p>Description: </p>
     * @param parentId
     * @return
     */
    private List<?> getCatList(long parentId) {

        //创建查询条件
        TbItemCatExample example = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        //执行查询
        List<TbItemCat> list = itemCatMapper.selectByExample(example);

        //返回值list
        List resultList = new ArrayList<>();
        //向list中添加节点
        int count = 0;
        for (TbItemCat tbItemCat : list) {
            //判断是否为父节点
            if (tbItemCat.getIsParent()) {
                CatNode catNode = new CatNode();
                if (parentId == 0) {
                    catNode.setName("<a href='/products/" + tbItemCat.getId() + ".html'>" + tbItemCat.getName() + "</a>");
                } else {
                    catNode.setName(tbItemCat.getName());
                }
                catNode.setUrl("/products/" + tbItemCat.getId() + ".html");
                //递归调用子节点
                catNode.setItem(getCatList(tbItemCat.getId()));

                resultList.add(catNode);
                count++;
                //取出14条记录
                if (parentId == 0 && count >= 14) {
                    break;

                }
                //如果是叶子节点
            } else {
                resultList.add("/products/" + tbItemCat.getId() + ".html|" + tbItemCat.getName());
            }
        }

        return resultList;
    }

}
