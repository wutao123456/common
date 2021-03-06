package com.dlh.controller;

import com.dlh.service.ConsumerService;
import com.dlh.service.ProducerService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.TextMessage;
import javax.jms.Topic;

/**
 * @author wutao
 * @email wutao56789@gmail.com
 * @date 2018/11/1 21:18
 */
@Controller
public class MessageController {
    private static Logger logger = Logger.getLogger(MessageController.class);

    @Resource(name = "activeMQQueue")
    private Destination destination;

    @Resource(name = "activeMQTopic")
    private Topic topic;

    //队列消息生产者
    @Resource(name = "producerService")
    private ProducerService producer;

    //队列消息消费者
    @Resource(name = "consumerService")
    private ConsumerService consumer;

    @RequestMapping(value = "/SendMessage", method = RequestMethod.GET)
    @ResponseBody
    public void send(String msg) {
        logger.info(Thread.currentThread().getName()+"------------send to jms Start");
        producer.sendMessage(msg);
        logger.info(Thread.currentThread().getName()+"------------send to jms End");
    }

    @RequestMapping(value = "/SendTopicMessage", method = RequestMethod.GET)
    @ResponseBody
    public void sendTopic(String msg) {
        logger.info(Thread.currentThread().getName()+"------------send to jms Start");
        producer.sendTopicMessage(topic,msg);
        logger.info(Thread.currentThread().getName()+"------------send to jms End");
    }

    @RequestMapping(value= "/ReceiveMessage",method = RequestMethod.GET)
    @ResponseBody
    public void receive(){
        logger.info(Thread.currentThread().getName()+"------------receive from jms Start");
        TextMessage tm = consumer.receive(destination);
        logger.info(Thread.currentThread().getName()+"------------receive from jms End");
    }

}
