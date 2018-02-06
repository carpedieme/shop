package com.tt.test;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrResponse;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.testng.annotations.Test;

import java.io.IOException;

public class SolrTest {

    @Test
    public void addDocument() throws Exception {

        //创建连接
        SolrServer solrServer = new HttpSolrServer("http://192.168.78.130:8080/solr");
        //创建文档对象
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", "solr客户端");
        document.addField("item_desc", "测试商品述");
        //将文档对象写入库中
        solrServer.add(document);
        //提交
        solrServer.commit();
    }

    @Test
    public void delDocument() throws Exception {

        //创建连接
        SolrServer solrServer = new HttpSolrServer("http://192.168.78.130:8080/solr");
//        solrServer.deleteById("solr客户端");
        solrServer.deleteByQuery("*:*");//删除所有
        //提交
        solrServer.commit();
    }
    @Test
    public void queryList() throws SolrServerException {
        //创建连接
        SolrServer solrServer = new HttpSolrServer("http://192.168.78.130:8080/solr");
        //创建查询对象
        SolrQuery query = new SolrQuery();
        //设置查询条件
        query.setQuery("*:*");
        //分页
        query.setStart(20);
        query.setRows(50);
        //执行查询
        QueryResponse response =solrServer.query(query);
        //获取查询结果
        SolrDocumentList documentList=response.getResults();
        System.out.println("共查询到记录"+documentList.getNumFound());
        for (SolrDocument solrdocument:documentList) {
            System.out.println(solrdocument.get("id"));
            System.out.println(solrdocument.get("item_title"));
            System.out.println(solrdocument.get("item_price"));
            System.out.println(solrdocument.get("item_image"));
        }
    }
}
