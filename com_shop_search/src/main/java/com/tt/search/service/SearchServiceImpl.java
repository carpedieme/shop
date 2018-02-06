package com.tt.search.service;

import com.tt.search.dao.SearchDao;
import com.tt.search.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchServiceImpl implements SearchService {


    @Autowired
    private SearchDao searchDao;

    @Override
    public SearchResult search(String queryStr, int page, int rows) throws Exception {

        //创建查询对象
        SolrQuery solrQuery = new SolrQuery();
        //设置查询条件
        solrQuery.setQuery(queryStr);
        //分页
        solrQuery.setStart((page-1)*rows);
        solrQuery.setRows(rows);
        //设置默认搜索域
        solrQuery.set("df","item_keywords");
        //设置打开高亮显示
        solrQuery.setHighlight(true);
        //添加需要高亮显示的字段
        solrQuery.addHighlightField("item_title");
        //设置高亮显示的格式
        solrQuery.setHighlightSimplePre("<em style=\"color:red\">");
        solrQuery.setHighlightSimplePost("</em>");
        //执行查询
        SearchResult respon = searchDao.search(solrQuery);
        //计算查询结果总页数
        long recount=respon.getRecordCount();
        long pagecount=recount/rows;
        if (recount % rows > 0) {
            pagecount++;
        }
        respon.setPageCount(pagecount);
        respon.setCurPage(page);

        return respon;

    }
}
