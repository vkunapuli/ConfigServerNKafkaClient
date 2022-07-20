package com.sssystems.kafkademo.config;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;

import java.util.Properties;


public class BitBucketProeprtiesListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        ConfigurableEnvironment environment = event.getEnvironment();
        Properties props = new Properties();
        props.put("spring.datasource.url", "<my value>");
        environment.getPropertySources().addFirst(new PropertiesPropertySource("myProps", props));
    }
}
