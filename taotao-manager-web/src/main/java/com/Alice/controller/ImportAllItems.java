package com.Alice.controller;

import com.Alice.common.pojo.TaotaoResult;
import com.Alice.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Alice
 * @date 2018/8/13/013
 */
@Controller
public class ImportAllItems {

    @Autowired
    private SearchService searchService;

    @RequestMapping("/index/importAll")
    @ResponseBody
    public TaotaoResult importAll() throws Exception{
        return searchService.importAllSearchItems();
    }
}
