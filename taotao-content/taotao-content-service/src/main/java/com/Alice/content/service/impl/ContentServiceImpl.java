package com.Alice.content.service.impl;

import com.Alice.common.pojo.TaotaoResult;
import com.Alice.common.utils.JsonUtils;
import com.Alice.content.jedis.JedisClient;
import com.Alice.content.service.ContentService;
import com.Alice.mapper.TbContentMapper;
import com.Alice.pojo.TbContent;
import com.Alice.pojo.TbContentExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private JedisClient client;

    @Autowired
    private TbContentMapper contentMapper;

    @Value("${CONTENT_KEY}")
    private String CONTENT_KEY;

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
        //当添加内容的时候清空此内容的分类下的所有的缓存
        try {
            client.hdel(CONTENT_KEY, content.getCategoryId() + "");
            System.out.println("当插入时清空缓存");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return TaotaoResult.ok(content);
    }

    /**
     * 根据分类id查询其下的内容
     * @param category
     * @return
     */
    public List<TbContent> getContentListBycatId(Long category) {
        //添加缓存不能影响正常的业务逻辑

        //判断redis是否有数据，如果有直接从redis中返回
        try {
            String jsonstr = client.hget(CONTENT_KEY, category + "");//从redis数据库中获取内容分类下的所有的内容
            if (StringUtils.isNotBlank(jsonstr)) {
                System.out.println("有缓存！！！！");
                return JsonUtils.jsonToList(jsonstr,TbContent.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //1.注入mapper
        //2.创建example
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        //3.设置查询条件
        criteria.andCategoryIdEqualTo(category);
        //4.查询
        List<TbContent> list = contentMapper.selectByExample(example);
        //5.将数据写入到redis
        try {
            System.out.println("没有缓存！！！！");
            client.hset(CONTENT_KEY,category+"",JsonUtils.objectToJson(list));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //6.返回结果
        return list;
    }
}
