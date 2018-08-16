package com.Alice.item.listener;


import com.Alice.item.pojo.Item;
import com.Alice.pojo.TbItem;
import com.Alice.pojo.TbItemDesc;
import com.Alice.service.ItemService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * 接收消息的监听器
 * @author Alice
 * @date 2018/8/15/015
 */
public class ItemChangeMessageListener implements MessageListener{

    @Autowired
    private ItemService itemService;

    @Autowired
    private FreeMarkerConfig freeMarkerConfig;

    @Value("${HTML_OUT_PATH}")
    private String HTML_OUT_PATH;

    @Override
    public void onMessage(Message message) {
        try {
            //判断消息的类型是否为textmessage
            if(message instanceof TextMessage){
                //如果是 获取商品的id
                TextMessage idMessage = (TextMessage) message;
                Long id = Long.parseLong(idMessage.getText());
                TbItem tbItem = itemService.getItemById(id);
                Item item = new Item(tbItem);
                TbItemDesc itemDesc = itemService.getItemDescById(id);
                Configuration configuration = freeMarkerConfig.getConfiguration();
                Template template = configuration.getTemplate("item.ftl");
                Map data = new HashMap();
                data.put("item",item);
                data.put("itemDesc",itemDesc);
                Writer out = new FileWriter(new File(HTML_OUT_PATH + id + ".html"));
                template.process(data,out);
                out.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
