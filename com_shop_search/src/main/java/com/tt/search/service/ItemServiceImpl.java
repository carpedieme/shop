package com.tt.search.service;

import com.tt.search.mapper.ItemMapper;
import com.tt.search.pojo.Item;
import com.tt.util.TaotaoResult;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemMapper itemMapper;
    //调用solr客户端
    @Autowired
    private SolrServer solrServer;

    @Override
    public TaotaoResult importAllItems() {

        try {
            //查询商品类表
            List<Item> list = itemMapper.getItemList();
            //把商品信息写入索引库
            for (Item item : list) {
                //创建一个solrInputDocument
                SolrInputDocument document = new SolrInputDocument();
                document.addField("id", item.getId());
                document.addField("item_title", item.getTitle());
                document.addField("item_sell_point", item.getSell_point());
                document.addField("item_price", item.getPrice());
                document.addField("item_image", item.getImage());
                document.addField("item_category_name", item.getCategory_name());
                document.addField("item_desc", item.getItem_des());
                //写入索引库
                solrServer.add(document);
            }
            //提交修改
            solrServer.commit();
        }catch(Exception e){

            e.printStackTrace();
            return  TaotaoResult.build(500,"solr检索时出异常了...."+e);
        }
        return TaotaoResult.ok();
    }
}
