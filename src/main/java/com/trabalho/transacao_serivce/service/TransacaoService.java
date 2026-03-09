package com.trabalho.transacao_serivce.service;


import com.trabalho.transacao_serivce.dto.request.TransacaoRequestDTO;
import com.trabalho.transacao_serivce.dto.response.TransacaoResponseDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransacaoService {
    //config do kafka
    //config do Redis


    public TransacaoResponseDTO enviaTransacao(TransacaoRequestDTO request){


    }

    public boolean verificaSaldoNoRedis(BigDecimal valorTransacao){

    }
}
