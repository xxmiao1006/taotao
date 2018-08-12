package com.Alice.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Alice.common.pojo.EasyUITreeNode;
import com.Alice.common.pojo.TaotaoResult;
import com.Alice.content.service.ContentCategoryService;
import com.Alice.mapper.TbContentCategoryMapper;
import com.Alice.pojo.TbContentCategory;
import com.Alice.pojo.TbContentCategoryExample;
import com.Alice.pojo.TbContentCategoryExample.Criteria;

/**
 * 内容分类管理service
 * @version 1.0
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private TbContentCategoryMapper mapper;


	@Override
	public List<EasyUITreeNode> getContentCategoryList(Long parentId) {
		//根据parentId查询子节点列表
		TbContentCategoryExample example = new TbContentCategoryExample();
		//设置查询条件
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		//执行查询
		List<TbContentCategory> list = mapper.selectByExample(example);
		List<EasyUITreeNode> nodes = new ArrayList<>();
		for (TbContentCategory category : list) {
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(category.getId());
			node.setState(category.getIsParent()?"closed":"open");
			node.setText(category.getName());
			nodes.add(node);
		}
		return nodes;
	}

	/**
	 * 添加分类
	 * @param parentId
	 * @param name
	 * @return
	 */
	public TaotaoResult creatContentCategory(Long parentId, String name) {
		//1.构建对象 补全其他属性
		TbContentCategory category = new TbContentCategory();
		category.setCreated(new Date());
		category.setIsParent(false);
		category.setName(name);
		category.setParentId(parentId);
		category.setSortOrder(1);
		category.setStatus(1);
		category.setUpdated(category.getCreated());
		//2.插入contentCategory表数据
		//3.返回taotaoresult包含内容分类的id;  需要主键返回
		mapper.insertSelective(category);

		//判断父节点是叶子节点，需要更新其为父节点
		TbContentCategory parent = mapper.selectByPrimaryKey(parentId);
		if(!parent.getIsParent()){
			parent.setIsParent(true);
		}
		mapper.updateByPrimaryKeySelective(parent);

		return TaotaoResult.ok(category);
	}

}
