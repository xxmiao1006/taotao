package com.Alice.content.service.impl;

import com.Alice.common.pojo.TaotaoResult;
import com.Alice.content.service.ContentService;
import com.Alice.mapper.TbContentMapper;
import com.Alice.pojo.TbContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Alice
 * @date 2018/8/5/005
 */
@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentMapper contentMapper;

    /**
     * 添加内容的逻辑
     * @param content
     * @return
     */
    public TaotaoResult saveContent(TbContent content) {
        //补全属性
        content.setCreated(new Date());
        content.setUpdated(content.getCreated());
        //插入
        contentMapper.insertSelective(content);
        return TaotaoResult.ok(content);
    }
}
