package com.example.demo.controller;

import com.example.demo.service.AsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HelloController {

    @Autowired
    private AsyncService asyncService;

    @GetMapping("/")
    public String hello(){
        log.info("start submit async");
        asyncService.executeAsync();
        log.info("end submit async");
        return "hello";
    }
}
