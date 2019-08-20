package com.example.demo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@ConfigurationProperties(prefix = "rocketmq.producer")
@Configuration
public class ProducerConfig {

    private String namesrvAddr;

    private String groupName;
}
