package com.awin.interview.transactionapi.outbox.publisher;

import com.awin.interview.transactionapi.model.OutboxEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaEventPublisher implements EventPublisher{

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${event.kafka.topic.transaction-events}")
    private String topicName;
    public KafkaEventPublisher(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publish(OutboxEvent event) {
        kafkaTemplate.send(topicName, event.getAggregateId(), event.getPayload());
    }
}
