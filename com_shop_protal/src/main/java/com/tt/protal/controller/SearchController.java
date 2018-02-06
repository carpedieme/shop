package com.tt.protal.controller;


import com.tt.protal.pojo.SearchResult;
import com.tt.protal.service.SearchService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 商品搜索controller层
 */
@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;


    @RequestMapping("/search")
    public String search(@RequestParam("q") String queryStr, @RequestParam(defaultValue = "1") Integer page, Model model) {

        if (!StringUtils.isBlank(queryStr)) {
            try {
                //解决乱码
                queryStr = new String(queryStr.getBytes("iso8859-1"), "utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        SearchResult result = searchService.search(queryStr, page);
        //想页面传递参数
        model.addAttribute("query", queryStr);
        model.addAttribute("totalPages", result.getPageCount());
        model.addAttribute("itemList", result.getItemList());
        model.addAttribute("page", page);
        return "search";
    }
}
