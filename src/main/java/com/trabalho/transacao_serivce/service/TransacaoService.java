package com.trabalho.transacao_serivce.service;


import com.trabalho.transacao_serivce.database.entity.enums.TipoConta;
import com.trabalho.transacao_serivce.database.oracle.repository.ClienteSaldoRepository;
import com.trabalho.transacao_serivce.dto.request.TransacaoRequestDTO;
import com.trabalho.transacao_serivce.dto.response.TransacaoResponseDTO;
import com.trabalho.transacao_serivce.utils.RedisUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransacaoService {
    RedisUtils redisUtils;
    ClienteSaldoRepository clienteSaldoRepository;

    //config do kafka
    //config do Redis


    public TransacaoResponseDTO processaTransacao(TransacaoRequestDTO request){

        Long idUsuario = request.getIdUsuario();
        BigDecimal valor =request.getValor();
        TipoConta tipoConta = request.getTipoConta();

       BigDecimal valorAtualizado = redisUtils.decrement(idUsuario,valor,tipoConta);

       atualizaSaldoNoBanco(valorAtualizado,idUsuario,tipoConta);


    }



    public void atualizaSaldoNoBanco(BigDecimal saldoAtualizado, Long idUsuario, TipoConta tipoConta){
        if(tipoConta.equals(TipoConta.CREDITO)){
            clienteSaldoRepository.updateSaldoCredito(saldoAtualizado);
        }
        if(tipoConta.equals(TipoConta.DEBITO)){
            clienteSaldoRepository.updateSaldoDebito(saldoAtualizado);
        }
    }
}
