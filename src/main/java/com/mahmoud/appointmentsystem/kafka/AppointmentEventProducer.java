package com.mahmoud.appointmentsystem.kafka;


import com.mahmoud.appointmentsystem.kafka.config.KafkaTopics;
import com.mahmoud.appointmentsystem.kafka.eventsDTO.AppointmentEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AppointmentEventProducer {


    private final KafkaTemplate<String, AppointmentEvent> kafkaTemplate;
    private final KafkaTemplate<String, String> kafkaStringTemplate;


    public void sendAppointmentEvent(AppointmentEvent event) {

        Message <AppointmentEvent> message = MessageBuilder.withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, "appointments-topic")
                .build();
        kafkaTemplate.send(message)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("Sent event [{}] to topic-partition {}-{} with offset {}",
                                event,
                                result.getRecordMetadata().topic(),
                                result.getRecordMetadata().partition(),
                                result.getRecordMetadata().offset());
                    } else {
                        log.error("Failed to send event: {}", event, ex);
                    }
                });
    }

    public void sendStringEvent(String eventMessage) {


        kafkaStringTemplate.send("appointments-topic", eventMessage)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("Sent event: {}", eventMessage);
                    } else {
                         log.error("Failed to send event: {}", eventMessage, ex);
                    }
                });
    }


}
