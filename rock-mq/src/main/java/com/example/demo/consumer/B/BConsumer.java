package com.example.demo.consumer.B;

import com.example.demo.consumer.Consumer;
import com.example.demo.dto.B.BDto;
import com.example.demo.dto.tag.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@Slf4j
public class BConsumer implements Consumer {
    @Override
    public String tageName() {
        return Tag.SEND_B.tagName();
    }

    @Override
    public Boolean consume(Serializable payload) {
        BDto bDto = (BDto) payload;
        log.info("consume B age is {}",bDto.getAge());
        return null;
    }
}
