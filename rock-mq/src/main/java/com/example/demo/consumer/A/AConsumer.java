package com.example.demo.consumer.A;

import com.example.demo.consumer.Consumer;
import com.example.demo.dto.A.ADto;
import com.example.demo.dto.tag.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@Slf4j
public class AConsumer implements Consumer {
    @Override
    public String tageName() {
        return Tag.SEND_A.tagName();
    }

    @Override
    public Boolean consume(Serializable payload) {
        ADto aDto = (ADto) payload;
        log.info("cusmer a name is {}",aDto.getName() );
        return true;
    }
}
