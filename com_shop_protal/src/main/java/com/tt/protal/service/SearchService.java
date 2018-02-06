package com.tt.protal.service;

import com.tt.protal.pojo.SearchResult;

public interface SearchService {


    SearchResult search(String queryStr,int page);
}
