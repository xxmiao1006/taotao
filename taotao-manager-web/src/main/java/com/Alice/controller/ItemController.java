package com.Alice.controller;

import com.Alice.common.pojo.EasyUIDataGridResult;
import com.Alice.common.pojo.TaotaoResult;
import com.Alice.pojo.TbItem;
import com.Alice.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Alice
 * @date 2018/8/3/003
 */
@Controller
public class ItemController {


    @Autowired
    private ItemService itemService;

    @RequestMapping(value = "/item/list",method = RequestMethod.GET)
    @ResponseBody
    public EasyUIDataGridResult getItemList(Integer page,Integer rows){
        return itemService.getItemList(page,rows);
    }


    @RequestMapping(value = "/item/save",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult addItem(TbItem item,String desc){
        TaotaoResult result = itemService.addItem(item, desc);
        return result;
    }


}
