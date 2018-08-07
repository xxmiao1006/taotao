package com.Alice.content.service;

import com.Alice.common.pojo.TaotaoResult;
import com.Alice.pojo.TbContent;

import java.util.List;

/**
 * 内容处理的接口
 *
 * @author Alice
 * @date 2018/8/5/005
 */
public interface ContentService {

    /**
     * 保存内容
     * @param content
     * @return
     */
    TaotaoResult saveContent(TbContent content);

    /**
     * 根据内容分类id查询其下的内容列表
     * @param category
     * @return
     */
    List<TbContent> getContentListBycatId(Long category);

}
