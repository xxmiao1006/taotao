package com.Alice.search.listener;

import com.Alice.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * 接收消息的监听器
 * @author Alice
 * @date 2018/8/15/015
 */
public class ItemChangeMessageListener implements MessageListener{

    @Autowired
    private SearchService searchService;
    @Override
    public void onMessage(Message message) {
        try {
        //判断消息的类型是否为textmessage
            if(message instanceof TextMessage){
                //如果是 获取商品的id
                TextMessage idMessage = (TextMessage) message;
                Long id = Long.parseLong(idMessage.getText());
                //通过商品的id查询数据 需要开发mapper
                searchService.updateItemById(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
