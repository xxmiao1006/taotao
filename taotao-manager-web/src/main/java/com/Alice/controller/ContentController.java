package com.Alice.controller;

import com.Alice.common.pojo.TaotaoResult;
import com.Alice.content.service.ContentService;
import com.Alice.pojo.TbContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Alice
 * @date 2018/8/5/005
 */
@Controller
public class ContentController {

    @Autowired
    private ContentService contentService;

    /**
     * 添加内容
     * @param content
     * @return
     */
    @RequestMapping(value = "/content/save",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult saveContent(TbContent content){
        return contentService.saveContent(content);
    }

}
