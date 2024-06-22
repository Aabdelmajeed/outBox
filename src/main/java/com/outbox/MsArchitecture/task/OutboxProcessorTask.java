package com.outbox.MsArchitecture.task;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.outbox.MsArchitecture.entity.Outbox;
import com.outbox.MsArchitecture.entity.User;
import com.outbox.MsArchitecture.repository.OutboxRepository;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class OutboxProcessorTask {

    private final OutboxRepository outboxRepository;

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String topic;
    private final ObjectMapper objectMapper;

    public OutboxProcessorTask(OutboxRepository outboxRepository,
                               KafkaTemplate<String, String> kafkaTemplate, @Value("${kafka.topic.user}") String topic, ObjectMapper objectMapper) {
        this.outboxRepository = outboxRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
        this.objectMapper = objectMapper;
    }

    @Scheduled(fixedRate = 5000)
    @Transactional
    public void process() throws JsonProcessingException {
        System.out.println("Task executed");

        List<Outbox> outboxes = outboxRepository.findTop10ByIsDelivered(false);

        for (Outbox outbox : outboxes) {
              kafkaTemplate.send(topic,  outbox.getMessage());
              outbox.setIsDelivered(true);
        }
    }

}