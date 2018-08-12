package com.Alice.controller;

import com.Alice.common.pojo.EasyUITreeNode;
import com.Alice.common.pojo.TaotaoResult;
import com.Alice.content.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author Alice
 * @date 2018/8/5/005
 */
@Controller
public class ContentCategoryController {

    @Autowired
    private ContentCategoryService contentCategoryService;

    /**
     * 列出所有分类信息
     * @param parentId
     * @return
     */
    @RequestMapping(value = "/content/category/list",method = RequestMethod.GET)
    @ResponseBody
    public List<EasyUITreeNode> getContentCategoryList(@RequestParam(value = "id",defaultValue = "0") long parentId){
        return contentCategoryService.getContentCategoryList(parentId);
    }

    /**
     * 添加分类节点
     * @param parentId
     * @param name
     * @return
     */
    @RequestMapping(value = "content/category/create",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult create(Long parentId ,String name){
        return contentCategoryService.creatContentCategory(parentId,name);
    }

}
