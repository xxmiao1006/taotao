package com.Alice.content.service;

import com.Alice.common.pojo.EasyUITreeNode;
import com.Alice.common.pojo.TaotaoResult;

import java.util.List;

/**
 * @author Alice
 * @date 2018/8/5/005
 */
public interface ContentCategoryService {

    //通过节点的id查询该节点的子节点列表
    List<EasyUITreeNode> getContentCategoryList(Long parentId);

    //添加内容分类
    TaotaoResult creatContentCategory(Long parentId,String name);


}
