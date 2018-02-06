package com.tt.search.controller;


import com.tt.search.pojo.SearchResult;
import com.tt.search.service.SearchService;
import com.tt.util.TaotaoResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;


    @RequestMapping(value = "/query", method = RequestMethod.GET)
    @ResponseBody
    public TaotaoResult search(@RequestParam("q") String queryStr, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "60") Integer rows) {

        if(StringUtils.isBlank(queryStr)){
            return TaotaoResult.build(400,"查询条件不能为空");
        }
        SearchResult result=null;
        try {
            //解决乱码问题
            queryStr=new String(queryStr.getBytes("iso8859-1"),"utf-8");
            result = searchService.search(queryStr, page, rows);
        }catch (Exception e){
            e.printStackTrace();
            return TaotaoResult.build(500,"搜索出错。。。"+e);
        }

        return TaotaoResult.ok(result);
    }
}
