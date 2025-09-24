package com.mahmoud.appointmentsystem.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopics {

    public static final String APPOINTMENTS_TOPIC = "appointments-topic";

    @Bean
    public NewTopic appointmentsTopic() {

        return TopicBuilder.name(APPOINTMENTS_TOPIC)
                .partitions(2)
                .replicas(2)
                .build();
    }
}
