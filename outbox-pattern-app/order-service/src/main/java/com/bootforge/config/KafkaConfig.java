package com.bootforge.config;

import com.bootforge.constants.GlobalConstants;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic orderCreatedTopic(){
        return TopicBuilder
                .name(GlobalConstants.ORDER_CREATED_EVENT)
                .partitions(3)
                .replicas(1)
                .build();
    }
}
