package com.awin.interview.transactionapi.outbox;

import com.awin.interview.transactionapi.model.OutboxEvent;
import com.awin.interview.transactionapi.model.OutboxEventStatus;
import com.awin.interview.transactionapi.outbox.publisher.EventPublisher;
import com.awin.interview.transactionapi.repository.OutboxEventRepository;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OutboxPublishScheduledJob {

    private final OutboxEventRepository outboxEventRepository;
    private final EventPublisher eventPublisher;

    public OutboxPublishScheduledJob(OutboxEventRepository outboxEventRepository, EventPublisher eventPublisher) {
        this.outboxEventRepository = outboxEventRepository;
        this.eventPublisher = eventPublisher;
    }

    @Scheduled(fixedDelay = 5000)
    @Transactional
    public void publishPendingOutboxEvents(){
        List<OutboxEvent> events = outboxEventRepository.findTop50ByStatusOrderByCreatedAtAsc(OutboxEventStatus.PENDING);
        for(OutboxEvent event : events){
            try {
                eventPublisher.publish(event);
                event.markPublished();
            } catch(RuntimeException ex){
                event.markFailed(ex.getMessage());
            }

        }
    }
}
