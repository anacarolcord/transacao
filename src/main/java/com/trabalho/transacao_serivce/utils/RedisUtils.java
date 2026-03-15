package com.trabalho.transacao_serivce.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trabalho.transacao_serivce.database.entity.enums.StatusTransacao;
import com.trabalho.transacao_serivce.database.entity.enums.TipoConta;
import com.trabalho.transacao_serivce.dto.response.TransacaoResponseDTO;
import com.trabalho.transacao_serivce.dto.response.TransacaoSaldoStatusDTO;
import com.trabalho.transacao_serivce.exceptions.SaldoInsuficienteException;
import com.trabalho.transacao_serivce.exceptions.SaldoNegativoException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class RedisUtils {

    public final RedisTemplate<String,String> redisTemplate;

    public TransacaoSaldoStatusDTO decrement(Long idUsuario, BigDecimal valor, TipoConta tipoContaEnum){
        TransacaoSaldoStatusDTO response = new TransacaoSaldoStatusDTO();

        String chave = "cliente: " + idUsuario;
        String tipoConta = tipoContaEnum.name();

        if(isValidTransacao(chave,valor, tipoConta)){

            Long centavos = valor.movePointRight(2).longValue();
            Long saldoPosDecremento = redisTemplate.opsForHash()
                    .increment(chave,tipoConta,-centavos);

            if(saldoPosDecremento < 0){
                redisTemplate.opsForHash().increment(chave,tipoConta,centavos);
                response.setStatusTransacao(StatusTransacao.REPROVADA);
               // throw new SaldoInsuficienteException();
            }

            response.setStatusTransacao(StatusTransacao.APROVADA);
            response.setSaldoAtualizado(new BigDecimal(saldoPosDecremento));


        }else{
            response.setStatusTransacao(StatusTransacao.REPROVADA);
        }

        return response;
    }

    public boolean isValidTransacao(String chave, BigDecimal valor, String tipoConta) {

        Object valorNoRedis = redisTemplate.opsForHash().get(chave, tipoConta);

        if(Objects.isNull(valorNoRedis)){
            return false;
        }

        BigDecimal saldoAtualCentavos = new BigDecimal(valorNoRedis.toString());
        BigDecimal valorTransacaoCentavos = valor.movePointRight(2);

        if (saldoAtualCentavos.compareTo(valorTransacaoCentavos) < 0){
            return false;
        }

        return true;

    }


}
