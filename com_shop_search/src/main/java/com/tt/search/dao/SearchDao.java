package com.tt.search.dao;

import com.tt.search.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;

public interface SearchDao {

    SearchResult search(SolrQuery query) throws  Exception;
}
