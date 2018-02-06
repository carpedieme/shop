package com.tt.search.service;

import com.tt.search.pojo.SearchResult;

public interface SearchService {


    SearchResult search(String queryStr,int page,int rows) throws Exception;
}
