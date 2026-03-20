package com.trabalho.transacao_serivce.service;

import com.trabalho.transacao_serivce.database.entity.enums.TipoConta;
import com.trabalho.transacao_serivce.database.oracle.repository.ClienteSaldoRepository;
import com.trabalho.transacao_serivce.dto.request.TransacaoRequestDTO;
import com.trabalho.transacao_serivce.dto.response.TransacaoResponseDTO;
import com.trabalho.transacao_serivce.dto.response.TransacaoSaldoStatusDTO;
import com.trabalho.transacao_serivce.messaging.config.kafka.TransacaoProducer;
import com.trabalho.transacao_serivce.utils.RedisUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


import static com.trabalho.transacao_serivce.mapper.TransacaoMapper.toResponse;

@Service
@RequiredArgsConstructor
public class TransacaoService {
    private final RedisUtils redisUtils;
    private final ClienteSaldoRepository clienteSaldoRepository;
    private final TransacaoProducer transacaoProducer;

    //config do kafka


    public TransacaoResponseDTO processaTransacao(TransacaoRequestDTO request){

        Long idUsuario = request.getIdUsuario();
        BigDecimal valor =request.getValor();
        TipoConta tipoConta = request.getTipoConta();

        TransacaoSaldoStatusDTO responseComStatus = redisUtils.decrement(idUsuario,valor,tipoConta);

        atualizaSaldoNoBanco(responseComStatus,idUsuario,tipoConta);

        transacaoProducer.enviarTransacao(toResponse(request,responseComStatus));

        return toResponse(request,responseComStatus);
    }



    public void atualizaSaldoNoBanco(TransacaoSaldoStatusDTO saldoStatus, Long idUsuario, TipoConta tipoConta){

        BigDecimal saldoAtualizado = saldoStatus.getSaldoAtualizado();
        if(tipoConta.equals(TipoConta.CREDITO) && saldoAtualizado.compareTo(BigDecimal.ZERO) >= 0){

            clienteSaldoRepository.updateSaldoCredito(idUsuario,saldoAtualizado);


        }
        if(tipoConta.equals(TipoConta.DEBITO) && saldoAtualizado.compareTo(BigDecimal.ZERO) >= 0){
            clienteSaldoRepository.updateSaldoDebito(idUsuario, saldoAtualizado);
        }
    }


}
