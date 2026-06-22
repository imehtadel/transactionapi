package com.awin.interview.transactionapi.outbox.publisher;

import com.awin.interview.transactionapi.model.OutboxEvent;

public interface EventPublisher {

    public void publish(OutboxEvent event);
}
