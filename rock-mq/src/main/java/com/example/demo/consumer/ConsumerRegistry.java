package com.example.demo.consumer;

import com.example.demo.ConsumerConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class ConsumerRegistry implements ApplicationContextAware, ApplicationRunner {

    @Autowired
    private ConsumerConfig consumerConfig;

    private Map<String,Consumer> consumerMap;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        consumerMap = new HashMap<>();
        Map<String,Consumer> beanMap = applicationContext.getBeansOfType(Consumer.class);
        beanMap.forEach((beanName, messageConsumer) -> {
            consumerMap.put(messageConsumer.tageName(), messageConsumer);
        });

    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
//        System.out.println("456");
        consumerMap.forEach((tagName,messageConsumer)->{
            try {
                registry("my_topic",tagName);
            } catch (MQClientException e) {
                e.printStackTrace();
            }
        });
    }

     private void registry(String topic, String tag) throws MQClientException {
        log.info("开启[" + topic + ":" + tag + "]消费者");
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerConfig.getGroupName());
        consumer.setNamesrvAddr(consumerConfig.getNamesrvAddr());
        // 程序第一次启动从消息队列头获取数据
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        // 可以修改每次消费消息的数量，默认设置是每次消费一条
        consumer.setConsumeMessageBatchMaxSize(1);

        // 设置Consumer的消费策略
        // CONSUME_FROM_LAST_OFFSET 默认策略，从该队列最尾开始消费，即跳过历史消息
        // CONSUME_FROM_FIRST_OFFSET 从队列最开始开始消费，即历史消息（还储存在broker的）全部消费一遍
        // CONSUME_FROM_TIMESTAMP 从某个时间点开始消费，和setConsumeTimestamp()配合使用，默认是半个小时以前
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        // 设置consumer所订阅的Topic和Tag，*代表全部的Tag
        // consumer.subscribe("TopicTest", "*");
        // 订阅topic下TAG为tag的消息
        consumer.subscribe(topic, tag);
        // 开启内部类实现监听
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgList, ConsumeConcurrentlyContext context) {
                Consumer con =  consumerMap.get(tag);
                con.consume(con.tageName());
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        // 调用start()方法启动Consumer
        consumer.start();
        log.info("RocketMQ Consumer Server 启动成功");
    }
}