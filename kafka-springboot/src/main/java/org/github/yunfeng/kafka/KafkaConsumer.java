package org.github.yunfeng.kafka;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumer {
    @KafkaListener(id = "test-group", topicPartitions = {
        @TopicPartition(topic = "test", partitions = {
            "0",
            "1"
        })
    }, containerFactory = "kafkaListenerContainerFactory", concurrency = "2", errorHandler = "myErrorHandler")
    public void listener(List<ConsumerRecord<String, String>> records, Acknowledgment acknowledgment) {
        for (ConsumerRecord<String, String> consumerRecord : records) {
            log.info("partition:{}, offset:{}, value:{}", consumerRecord.partition(), consumerRecord.offset(),
                     consumerRecord.value()
            );
        }
        acknowledgment.acknowledge();
    }
}
