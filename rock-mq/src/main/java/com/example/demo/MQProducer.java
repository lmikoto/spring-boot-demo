package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class MQProducer {

    @Autowired
    private ProducerConfig producerConfig;

    private DefaultMQProducer producer;

    @Bean
    @ConditionalOnProperty(prefix = "rocketmq.producer", value = "default", havingValue = "true")
    public DefaultMQProducer defaultProducer() throws MQClientException {
        log.info(producerConfig.toString());
        producer = new DefaultMQProducer(producerConfig.getGroupName());
        producer.setNamesrvAddr(producerConfig.getNamesrvAddr());
        producer.setVipChannelEnabled(false);
        producer.setRetryTimesWhenSendAsyncFailed(10);
        producer.start();
        log.info("RocketMQ Producer Server 启动成功");
        return producer;
    }

    public String send(Message message) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        StopWatch stop = new StopWatch();
        stop.start();
        SendResult result = producer.send(message);
        System.out.println("发送响应 - MsgId:" + result.getMsgId() + ", 发送状态:" + result.getSendStatus());
        stop.stop();
        return "{\"MsgId\":\"" + result.getMsgId() + "\"}";
    }

    public void send(Message message, SendCallback callback) throws InterruptedException, RemotingException, MQClientException {
        StopWatch stop = new StopWatch();
        stop.start();
        producer.send(message, callback);
        stop.stop();
    }
}
