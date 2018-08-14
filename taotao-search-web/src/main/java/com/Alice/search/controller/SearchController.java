package com.Alice.search.controller;

import com.Alice.common.pojo.SearchResult;
import com.Alice.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Alice
 * @date 2018/8/13/013
 */
@Controller
public class SearchController {

    @Value("${ITEM_ROWS}")
    private Integer ITEM_ROWS;

    @Autowired
    private SearchService searchService;

    /**
     * 根据条件搜索商品的数据
     *
     * @param page
     * @param queryString
     * @return
     */
    @RequestMapping("/search")
    public String search(@RequestParam(defaultValue = "1") Integer page, @RequestParam(value = "q") String queryString, Model model) throws Exception {
        queryString = new String(queryString.getBytes("iso-8859-1"),"UTF-8");


        SearchResult result = searchService.search(queryString, page, ITEM_ROWS);
        model.addAttribute("query", queryString);
        model.addAttribute("totalPages", result.getTotalPages());
        model.addAttribute("itemList", result.getItemList());
        model.addAttribute("page", page);

        return "search";
    }



}
