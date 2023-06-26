package com.flab.fkream.kafka;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

/**
 * 토픽 생성
 */

@Configuration
public class KafkaTopicConfig {
   @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    public final static String COMPLETION_DEAL_PRICE = "complete_deal_price";


    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic newTopic() {
        return new NewTopic(COMPLETION_DEAL_PRICE, 1, (short) 3);
    }
}

