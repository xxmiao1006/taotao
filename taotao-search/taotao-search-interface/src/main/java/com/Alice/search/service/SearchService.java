package com.Alice.search.service;

import com.Alice.common.pojo.SearchResult;
import com.Alice.common.pojo.TaotaoResult;

/**
 * @author Alice
 * @date 2018/8/13/013
 */
public interface SearchService {

    //导入所有的商品数据到索引库
    TaotaoResult importAllSearchItems() throws Exception;

    //根据搜索的条件查询搜索的结果
    /**
     *
     * @param queryString 查询的主条件
     * @param page  查询的当前的页码
     * @param rows  每页显示的行数
     * @return
     * @throws Exception
     */
    SearchResult search(String queryString ,Integer page,Integer rows) throws Exception;

    TaotaoResult updateItemById(Long itemId) throws Exception;

}
