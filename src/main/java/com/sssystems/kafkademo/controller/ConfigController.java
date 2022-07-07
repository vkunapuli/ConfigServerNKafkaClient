package com.sssystems.kafkademo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RefreshScope
@RestController
public class ConfigController {
    private static final Logger logger =
            LoggerFactory.getLogger(ConfigController.class);
    @Value("${msg:could not connect to config server}")
    private String msg;

    @Value("${user:role:unknown}")
    private String role;


    @GetMapping("/msg")
    public String getMsg() {
        return this.role;
    }
}
