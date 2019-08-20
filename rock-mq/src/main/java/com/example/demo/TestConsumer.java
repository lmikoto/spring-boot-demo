package com.example.demo;

import com.example.demo.consumer.Consumer;
import org.springframework.stereotype.Component;

@Component
public class TestConsumer implements Consumer {
    @Override
    public String tageName() {
        return "hahahaha";
    }

    @Override
    public Boolean consume(String payload) {
        System.out.println(payload);
        return null;
    }
}
