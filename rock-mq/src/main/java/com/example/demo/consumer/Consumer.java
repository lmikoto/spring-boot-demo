package com.example.demo.consumer;

import java.io.Serializable;

public interface Consumer {

    String tageName();

    Boolean consume(Serializable payload);
}
