package com.trabalho.transacao_serivce.utils;

import com.trabalho.transacao_serivce.exceptions.SaldoInsuficienteException;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.math.BigDecimal;
import java.util.Objects;

public class RedisUtils {
    public RedisTemplate<String,String> redisTemplate;

    public BigDecimal decrement(Long idUsuario, BigDecimal valor){

        HashOperations<String,String,String> hashOps = redisTemplate.opsForHash();

        validaSaldo(idUsuario,valor);

        Long centavos = valor.movePointRight(2).longValue();
        Long saldoPosTransacao = redisTemplate.opsForValue().decrement( String.valueOf(idUsuario),centavos);
        BigDecimal saldoEmDecimal = BigDecimal.valueOf(saldoPosTransacao).movePointLeft(2);



    }

    public void validaSaldo(Long idUsuario, BigDecimal valor){

       String valorAtual = redisTemplate.opsForValue().get(String.valueOf(idUsuario));

       if(Objects.isNull(valorAtual)){
           throw new SaldoInsuficienteException();
       }
    }


}
