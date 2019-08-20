package com.example.demo.consumer;

public interface Consumer {

    String tageName();

    Boolean consume(String payload);
}
