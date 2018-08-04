package com.Alice.service;

import com.Alice.common.pojo.EasyUITreeNode;

import java.util.List;

/**
 * 商品类目的逻辑
 * @author Alice
 * @date 2018/8/4/004
 */
public interface ItemCatService {

    List<EasyUITreeNode> getItemCatList(long parentId);
}
