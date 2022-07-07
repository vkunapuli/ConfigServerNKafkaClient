package com.sssystems.kafkademo.listner;

import com.sssystems.kafkademo.records.KafkaRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Headers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;
import java.util.stream.StreamSupport;

@Component
public class KafkaConsumerListener {
    private CountDownLatch latch;
    private static final Logger logger =
            LoggerFactory.getLogger(KafkaConsumerListener.class);
    private final int messagesPerRequest;
    @Value("${tpd.topic-name}")
    private String topicName;

    public KafkaConsumerListener(@Value("${tpd.messages-per-request}")
                                 final int messagesPerRequest)
    {
        latch = new CountDownLatch(messagesPerRequest);
        this.messagesPerRequest = messagesPerRequest;
    }
    public CountDownLatch getLatch() {
        return latch;
    }

    @KafkaListener(topics = "${tpd.topic-name}", clientIdPrefix = "string", groupId = "kafka-demo3",
            containerFactory = "kafkaListenerStringContainerFactory")
    public void listenASString(ConsumerRecord<String, String> cr,
                               @Payload String payload) {
        logger.info("Logger 2 [String] received key {}: Type [{}] | Payload: {} | Record: {}", cr.key(),
                typeIdHeader(cr.headers()), payload, cr.toString());
        latch.countDown();
    }

    //"kafka-topic-test"
    @KafkaListener(topics = "${tpd.topic-name}", clientIdPrefix = "json", groupId = "kafka-demo3",
            containerFactory = "kafkaListenerContainerFactory")
    public void listenAsObject(ConsumerRecord<String, KafkaRecord> cr,
                               @Payload KafkaRecord payload) {
        logger.info("Logger 1 [JSON] received key {}: Type [{}] | Payload: {} | Record: {}", cr.key(),
                typeIdHeader(cr.headers()), payload, cr.toString());
        latch.countDown();

    }

    @KafkaListener(topics = "${tpd.topic-name}", clientIdPrefix = "bytearray", groupId = "kafka-demo3",
            containerFactory = "kafkaListenerByteArrayContainerFactory")
    public void listenAsByteArray(ConsumerRecord<String, byte[]> cr,
                                  @Payload byte[] payload) {
        logger.info("Logger 3 [ByteArray] received key {}: Type [{}] | Payload: {} | Record: {}", cr.key(),
                typeIdHeader(cr.headers()), payload, cr.toString());
        latch.countDown();
    }

    private static String typeIdHeader( Headers headers) {
        return StreamSupport.stream(headers.spliterator(), false)
                .filter(header -> header.key().equals("__TypeId__"))
                .findFirst().map(header -> new String(header.value())).orElse("N/A");
    }
}
