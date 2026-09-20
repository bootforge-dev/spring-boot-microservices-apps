package com.bootforge.service;

import com.bootforge.entity.Outbox;
import com.bootforge.kafka.producer.OutboxOrderEventProducer;
import com.bootforge.repository.OutboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@EnableScheduling
public class OutboxService {

    private final OutboxRepository outboxRepository;
    private final OutboxOrderEventProducer outboxEventProducer;

    @Scheduled(fixedDelay = 60000)
    public void publishUnprocessedRecords(){

        //fetch unprocessed records
        List<Outbox> unProcessedList = outboxRepository.findByProcessedFalse();
        log.info("Unprocessed record count: "+ unProcessedList.size());

        //publish to Kafka
        unProcessedList.forEach(outbox -> {
            try {
                outboxEventProducer.publishOutboxOrder(outbox);
                outbox.setProcessed(true);
                outboxRepository.save(outbox);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        });

    }
}
