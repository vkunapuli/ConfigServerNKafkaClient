package com.sssystems.kafkademo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.JmsProperties;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.ApplicationContext;

@EnableConfigServer
@SpringBootApplication
public class KafkaDemoApplication {
    //private JmsTemplate jmsTemplate;
    public static void main(String[] args) {

        ApplicationContext ctx = SpringApplication.run(KafkaDemoApplication.class, args);

        //JmsTemplate jms = ctx.getBean(JmsTemplate.class);
        //jms.convertAndSend("javainuse", "test message");
    }

}
