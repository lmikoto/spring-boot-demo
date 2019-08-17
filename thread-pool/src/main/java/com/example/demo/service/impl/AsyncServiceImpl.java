package com.example.demo.service.impl;

import com.example.demo.service.AsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AsyncServiceImpl implements AsyncService {
    @Override
    @Async("asyncExecutor")
    public void executeAsync() {
        log.info("start async");
        try {
            Thread.sleep(5000);
        }catch (Exception e){
            e.printStackTrace();
        }
        log.info("end execute");
    }
}
