package com.example.demo.producer;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

@Configuration
@Slf4j
public class MQProducer {

    @Value("${mq.producer.groupName}")
    private String groupName;

    @Value("${mq.producer.namesrvAddr}")
    private String namesrvAddr;

    @Value("${mq.producer.topic}")
    private String topic;

    private DefaultMQProducer producer;

    @Bean
    @ConditionalOnProperty(prefix = "mq.producer", value = "default", havingValue = "true")
    public DefaultMQProducer defaultProducer() throws MQClientException {
        producer = new DefaultMQProducer(groupName);
        producer.setNamesrvAddr(namesrvAddr);
        producer.setVipChannelEnabled(false);
        producer.setRetryTimesWhenSendAsyncFailed(10);
        producer.start();
        log.info("RocketMQ Producer Server 启动成功");
        return producer;
    }

    public void send(String tags, Serializable body) throws RemotingException, MQClientException, InterruptedException {
        Message message = new Message(topic,tags, SerializationUtils.serialize(body));
        StopWatch stop = new StopWatch();
        stop.start();
        producer.send(message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("传输成功");
                log.info(JSON.toJSONString(sendResult));
            }
            @Override
            public void onException(Throwable e) {
                log.error("传输失败", e);
            }
        });
        stop.stop();
    }
}
