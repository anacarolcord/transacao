package com.trabalho.transacao_serivce.messaging.config.kafka;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "app.kafka")
public class KafkaTopicConfig {

    private Map<String, String> routing;

    public Map<String, String> getRouting() {
        return routing;
    }

    public void setRouting(Map<String, String> routing) {
        this.routing = routing;
    }

}
