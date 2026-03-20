package com.trabalho.transacao_serivce.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trabalho.transacao_serivce.database.entity.enums.StatusTransacao;
import com.trabalho.transacao_serivce.database.entity.enums.TipoConta;
import com.trabalho.transacao_serivce.dto.response.TransacaoSaldoStatusDTO;
import com.trabalho.transacao_serivce.exceptions.TransacaoInvalidaException;
import com.trabalho.transacao_serivce.exceptions.ValorInvalidoException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class RedisUtils {

    public final RedisTemplate<String,String> redisTemplate;
    private final ObjectMapper objectMapper;

    public TransacaoSaldoStatusDTO decrement(Long idUsuario, BigDecimal valor, TipoConta tipoContaEnum){
        TransacaoSaldoStatusDTO response = new TransacaoSaldoStatusDTO();

        String chave = "cliente" + idUsuario;
        String tipoConta = tipoContaEnum.name();

        if(isValidTransacao(chave,valor, tipoConta)){

            Long centavos = valor.movePointRight(2).longValue();
            Long saldoPosDecremento = redisTemplate.opsForHash()
                    .increment(chave,tipoConta,-centavos);

            BigDecimal saldoEmReaisAtualizado = BigDecimal.valueOf(saldoPosDecremento).movePointLeft(2);

            if(saldoEmReaisAtualizado.compareTo(BigDecimal.ZERO) < 0){
                Long saldoAtual = redisTemplate.opsForHash().increment(chave,tipoConta,centavos);
                response.setStatusTransacao(StatusTransacao.REPROVADA);
                response.setSaldoAtualizado(BigDecimal.valueOf(saldoAtual).movePointLeft(2));

            }else{
                response.setStatusTransacao(StatusTransacao.APROVADA);
                response.setSaldoAtualizado(saldoEmReaisAtualizado);
            }

        }else{
            throw new TransacaoInvalidaException();
        }
        return response;
    }

    public boolean isValidTransacao(String chave, BigDecimal valor, String tipoConta){


        if(valor.compareTo(BigDecimal.ZERO) == 0 || valor.compareTo(BigDecimal.ZERO) < 0){
            throw new ValorInvalidoException();
        }

        Object valorNoRedis = redisTemplate.opsForHash().get(chave, tipoConta);
        Long valorRedisConvertido= objectMapper.convertValue(valorNoRedis, Long.class);

        if(Objects.isNull(valorNoRedis)){
            return false;
        }else{

            Long valorTransacaoCentavos = valor.movePointRight(2).longValue();


            if (valorRedisConvertido.compareTo(valorTransacaoCentavos) < 0){
                return false;
            }

            return true;

        }


    }




}
