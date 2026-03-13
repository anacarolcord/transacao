package com.trabalho.transacao_serivce.service;


import com.trabalho.transacao_serivce.dto.request.TransacaoRequestDTO;
import com.trabalho.transacao_serivce.dto.response.TransacaoResponseDTO;
import com.trabalho.transacao_serivce.utils.RedisUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransacaoService {
    RedisUtils redisUtils;
    //config do kafka
    //config do Redis


    public TransacaoResponseDTO enviaTransacao(TransacaoRequestDTO request){

        redisUtils.decrement(request.getIdUsuario(), ,request.getValor());


    }

    public boolean saldoEhPositivo(Long idUsuario,BigDecimal valorTransacao){

        BigDecimal valorAtual = redisUtils.decrement(idUsuario,valorTransacao);
        if(valorAtual.compareTo(BigDecimal.ZERO) > 0){
            return true;
        }

        return false;
    }
}
