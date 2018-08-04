package com.Alice.controller;

import com.Alice.common.pojo.EasyUITreeNode;
import com.Alice.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 商品分类管理的controller
 * @author Alice
 * @date 2018/8/4/004
 */
@Controller
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;

    @RequestMapping("/item/cat/list")
    @ResponseBody
    public List<EasyUITreeNode> getItemList(@RequestParam(name = "id",defaultValue = "0") Long parendId){

        return itemCatService.getItemCatList(parendId);
    }


}
