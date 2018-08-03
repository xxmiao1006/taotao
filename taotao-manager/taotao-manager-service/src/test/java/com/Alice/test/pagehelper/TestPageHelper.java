package com.Alice.test.pagehelper;

import com.Alice.mapper.TbItemMapper;
import com.Alice.pojo.TbItem;
import com.Alice.pojo.TbItemExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * @author Alice
 * @date 2018/8/3/003
 */

public class TestPageHelper {

    @Test
    public void testhelper(){
        //1.初始化spirng容器
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
        //2.获取mapper代理对象
        TbItemMapper itemMapper = context.getBean(TbItemMapper.class);
        //3.设置分页信息
        PageHelper.startPage(1,3);
        //4.调用mapper方法
        TbItemExample example = new TbItemExample();
        List<TbItem> list = itemMapper.selectByExample(example);
        List<TbItem> list2 = itemMapper.selectByExample(example);

        PageInfo<TbItem> info = new PageInfo<>(list);

        //5.遍历结果
        System.out.println(list2.size());

    }



}
