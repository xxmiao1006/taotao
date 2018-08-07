package com.Alice.portal.controller;

import com.Alice.common.utils.JsonUtils;
import com.Alice.content.service.ContentService;
import com.Alice.pojo.TbContent;
import com.Alice.portal.pojo.AD1Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 *展示门户首页
 * @author Alice
 * @date 2018/8/5/005
 */
@Controller
public class PageController {

    @Autowired
    private ContentService contentService;

    @Value("${AD1_CATEGORY_ID}")
    private Long categoryId;

    @Value("${AD1_WIDTH}")
    private Integer AD1_WIDTH;

    @Value("${AD1_WIDTH_B}")
    private Integer AD1_WIDTH_B;

    @Value("${AD1_HEIGHT}")
    private Integer AD1_HEIGHT;

    @Value("${AD1_HEIGHT_B}")
    private Integer AD1_HEIGHT_B;

    @RequestMapping("/index")
    public String showIndex(Model model){
        //添加业务逻辑 根据内容分类的id 查询内容列表
        List<TbContent> contentList = contentService.getContentListBycatId(categoryId);
        //转换成自定义的pojo
        List<AD1Node> nodes = new ArrayList<AD1Node>();
        for (TbContent tbContent : contentList) {
            AD1Node node = new AD1Node();
            node.setAlt(tbContent.getSubTitle());
            node.setHeight(AD1_HEIGHT);
            node.setHeightB(AD1_HEIGHT_B);
            node.setHref(tbContent.getUrl());
            node.setSrc(tbContent.getPic());
            node.setSrcB(tbContent.getPic2());
            node.setWidth(AD1_WIDTH);
            node.setWidthB(AD1_WIDTH_B);
            nodes.add(node);
        }
        
        //传递数据
        model.addAttribute("ad1",JsonUtils.objectToJson(nodes));
        return "index";
    }
}
