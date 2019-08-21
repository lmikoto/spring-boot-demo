package com.example.demo.dto.tag;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Tag {

    SEND_A("send_a"),

    SEND_B("send_b");

    String tagName;

    public String tagName(){
        return this.tagName;
    }
}
