package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/")
    public String test(){
        return "hello";
    }

    @GetMapping("/exception")
    public String testException(){
        throw new RuntimeException("exception");
//        return "exception";
    }
}
