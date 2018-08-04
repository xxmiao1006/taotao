package com.Alice.service.impl;

import com.Alice.common.pojo.EasyUITreeNode;
import com.Alice.mapper.TbItemCatMapper;
import com.Alice.pojo.TbItemCat;
import com.Alice.pojo.TbItemCatExample;
import com.Alice.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品管理分类service
 * @author Alice
 * @date 2018/8/4/004
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private TbItemCatMapper itemCatMapper;


    @Override
    public List<EasyUITreeNode> getItemCatList(long parentId) {
        //根据父节点id查询子节点的列表
        TbItemCatExample example = new TbItemCatExample();
        //添加查询条件
        TbItemCatExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbItemCat> list = itemCatMapper.selectByExample(example);
        List<EasyUITreeNode> result = new ArrayList<EasyUITreeNode>();
        for (TbItemCat tbItemCat : list) {
            EasyUITreeNode node = new EasyUITreeNode();
            node.setId(tbItemCat.getId());
            node.setText(tbItemCat.getName());
            node.setState(tbItemCat.getIsParent() ? "closed" : "open");
            result.add(node);
        }
        return result;
    }
}
