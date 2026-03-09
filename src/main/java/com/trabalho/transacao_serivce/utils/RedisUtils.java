package com.trabalho.transacao_serivce.utils;

import org.springframework.data.redis.core.RedisTemplate;

import java.math.BigDecimal;

public class RedisUtils {
    public RedisTemplate<String,Object> redisTemplate;

    public BigDecimal decrement(Long idUsuario, BigDecimal valor){
         redisTemplate.opsForValue().decrement( idUsuario.toString(),valor.byteValueExact());
    }


}
