package com.Alice.search.mapper;

import com.Alice.common.pojo.SearchItem;

import java.util.List;

/**
 * 定义mapper关联查询3张表 查询出搜索时的数据
 * @author Alice
 * @date 2018/8/13/013
 */
public interface SearchItemMapper {

    //查询所有商品的数据
    List<SearchItem> getSearchItemList();

}
