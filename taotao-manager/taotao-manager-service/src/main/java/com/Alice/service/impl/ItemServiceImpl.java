package com.Alice.service.impl;

import com.Alice.common.pojo.EasyUIDataGridResult;
import com.Alice.common.pojo.TaotaoResult;
import com.Alice.common.utils.IDUtils;
import com.Alice.mapper.TbItemDescMapper;
import com.Alice.mapper.TbItemMapper;
import com.Alice.pojo.TbItem;
import com.Alice.pojo.TbItemDesc;
import com.Alice.pojo.TbItemExample;
import com.Alice.service.ItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.Date;
import java.util.List;

/**
 * @author Alice
 * @date 2018/8/3/003
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper tbItemMapper;

    @Autowired
    private TbItemDescMapper tbItemDescMapper;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Resource(name="itemAddtopic")
    private Destination destination;

    /**
     * 分页查询所有商品
     * @param page
     * @param rows
     * @return
     */
    public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
        //1.设置分页的信息，使用pagehelper
        if (page == null) {
            page=1;
        }
        if (rows == null) {
            rows=30;
        }
        PageHelper.startPage(page,rows);

        //2.注入mapper
        //3.创建example对象
        TbItemExample example = new TbItemExample();
        //4.根据mapper调用查询所有数据的方法
        List<TbItem> list = tbItemMapper.selectByExample(example);
        //5.获取分页的信息
        PageInfo<TbItem> info = new PageInfo<>(list);
        //6.封装到EasyUIDataGridResult
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setTotal((int) info.getTotal());
        result.setRows(info.getList());
        //7.返回
        return result;
    }

    /**
     * 添加商品的方法
     * @param item
     * @param desc
     * @return
     */
    public TaotaoResult addItem(TbItem item, String desc) {
        //生成商品id
        final long itemId = IDUtils.genItemId();
        //补全item属性
        item.setId(itemId);
        item.setStatus((byte) 1);
        item.setCreated(new Date());
        item.setUpdated(new Date());
        //向商品表插入数据
        tbItemMapper.insert(item);
        //创建一个商品描述表对应的pojo
        //补全pojo属性
        TbItemDesc itemDesc = new TbItemDesc();
        itemDesc.setItemId(itemId);
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(new Date());
        itemDesc.setUpdated(new Date());
        //向商品描述表插入数据
        tbItemDescMapper.insert(itemDesc);

        //添加发送消息的业务逻辑
        jmsTemplate.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(itemId+"");
            }
        });

        //返回结果
        return TaotaoResult.ok();
    }
}
