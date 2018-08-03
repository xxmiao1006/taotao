package com.Alice.service;

import com.Alice.common.pojo.EasyUIDataGridResult;

/**
 * 商品相关的service接口
 * @author Alice
 * @date 2018/8/3/003
 */
public interface ItemService {

    /**
     * 根据当前的页码和每页的行数进行查询
     * @param page
     * @param rows
     * @return
     */
    public EasyUIDataGridResult getItemList(Integer page ,Integer rows);
}
