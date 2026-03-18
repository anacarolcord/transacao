package com.trabalho.transacao_serivce.messaging.config.kafka;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trabalho.transacao_serivce.dto.response.TransacaoResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class TransacaoProducer {

    @Value("${spring.cloud.stream.bindings.enviarTransacao-out-0.destination}")
    public String topico;
    public final KafkaTemplate<String,String> kafkaTemplate;
    public final ObjectMapper objectMapper;

    public TransacaoProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void enviarTransacao(TransacaoResponseDTO transacao){

        String corpo = null;
        try{
            corpo = objectMapper.writeValueAsString(transacao);
        }catch (JsonProcessingException e){
            throw  new RuntimeException(e);
        }

        this.kafkaTemplate.send(topico,corpo);
    }
}
