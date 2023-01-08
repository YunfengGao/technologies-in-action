package org.github.yunfeng.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service("myErrorHandler")
@Slf4j
public class MyKafkaListenerErrorHandler implements KafkaListenerErrorHandler {
    @Override
    public Object handleError(Message<?> message, ListenerExecutionFailedException exception) {
        log.info("error:{}", message.getPayload(), exception);
        return null;
    }
}