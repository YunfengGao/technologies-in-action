package com.githug.yunfeng.kafka;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class Consumer {
    private static final String TOPIC = "test";

    public static void main(String[] args) {
        customer();
    }

    public static void customer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "test");
        props.put("enable.auto.commit", "false");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props)) {
            consumer.subscribe(Collections.singletonList(TOPIC));
            final int minBatchSize = 2;
            List<ConsumerRecord<String, String>> buffer = new ArrayList<>();
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(10));
                System.out.println(System.currentTimeMillis() / 1000);
                for (ConsumerRecord<String, String> record : records) {
                    buffer.add(record);
                    System.out.println(record.offset() + " " + record.value());
                }
                if (buffer.size() >= minBatchSize) {
                    consumer.commitSync();
                    buffer.clear();
                }
            }
        }
    }
}
