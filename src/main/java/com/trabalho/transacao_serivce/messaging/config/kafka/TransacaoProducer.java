package com.trabalho.transacao_serivce.messaging.config.kafka;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trabalho.transacao_serivce.dto.response.TransacaoResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class TransacaoProducer {

//    @Value("${spring.cloud.stream.bindings.enviarTransacao-out-0.destination}")
//    public String topico;
    public final KafkaTopicConfig kafkaTopicConfig;
    public final KafkaTemplate<String,String> kafkaTemplate;
    public final ObjectMapper objectMapper;

    public TransacaoProducer(KafkaTopicConfig kafkaTopicConfig, KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTopicConfig = kafkaTopicConfig;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void enviarTransacao(TransacaoResponseDTO transacao){

        String status = transacao.getStatusTransacao().name();
        String destino = kafkaTopicConfig.getRouting().get(status);

        if (Objects.isNull(destino)){
            throw new IllegalArgumentException();
        }

        String corpo = null;
        try{
            corpo = objectMapper.writeValueAsString(transacao);
        }catch (JsonProcessingException e){
            throw  new RuntimeException(e);
        }

        this.kafkaTemplate.send(destino,corpo);
    }
}
