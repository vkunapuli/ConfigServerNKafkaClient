package com.sssystems.kafkademo.controller;


import com.sssystems.kafkademo.listner.KafkaConsumerListener;
import com.sssystems.kafkademo.records.KafkaRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@RestController
public class KafkaDemoController {

    private static final Logger logger =
            LoggerFactory.getLogger(KafkaDemoController.class);
    private final KafkaTemplate<String, Object> template;
    private final KafkaConsumerListener listener;
    private final String topicName;
    private final int messagesPerRequest;

    public KafkaDemoController(
            final KafkaTemplate<String, Object> template,
            final KafkaConsumerListener listener,
            @Value("${tpd.queue-name}") final String topicName,
            @Value("${tpd.messages-per-request}") final int messagesPerRequest) {
        this.template = template;
        this.listener = listener;
        this.topicName = topicName;
        this.messagesPerRequest = messagesPerRequest;
    }

    @GetMapping("/test")
    public String hello() throws Exception {
        IntStream.range(0, messagesPerRequest)
                .forEach(i -> this.template.send(topicName, String.valueOf(i),
                        new KafkaRecord("A Test Message-" + i, i))
                );
        listener.getLatch().await(60, TimeUnit.SECONDS);
        logger.info("All messages received");
        return "All messages processed!";
    }

}