package com.Alice.content.service.impl;

import com.Alice.common.pojo.TaotaoResult;
import com.Alice.content.service.ContentService;
import com.Alice.mapper.TbContentMapper;
import com.Alice.pojo.TbContent;
import com.Alice.pojo.TbContentExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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

    /**
     * 根据分类id查询其下的内容
     * @param category
     * @return
     */
    public List<TbContent> getContentListBycatId(Long category) {
        //1.注入mapper
        //2.创建example
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        //3.设置查询条件
        criteria.andCategoryIdEqualTo(category);
        //4.查询
        List<TbContent> list = contentMapper.selectByExample(example);
        //5。返回结果
        return list;
    }
}
