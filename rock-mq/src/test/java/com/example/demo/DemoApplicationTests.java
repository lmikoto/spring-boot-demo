package com.example.demo;

import com.example.demo.dto.A.ADto;
import com.example.demo.dto.B.BDto;
import com.example.demo.dto.tag.Tag;
import com.example.demo.producer.MQProducer;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class DemoApplicationTests {

    @Autowired
    private MQProducer producer;

    @Test
    public void testDefaultRocketMQ() throws Exception {
        ADto aDto = new ADto();
        aDto.setName("tester");
        producer.send(Tag.SEND_A.tagName(),aDto);

        BDto bDto = new BDto();
        bDto.setAge(20);
        producer.send(Tag.SEND_B.tagName(),bDto);

        Thread.sleep(5000L);
    }
}
