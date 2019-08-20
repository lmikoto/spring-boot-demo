package com.example.demo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "rocketmq.consumer")
@Configuration
@Data
public class ConsumerConfig {
    private String groupName;
    private String namesrvAddr;
}
