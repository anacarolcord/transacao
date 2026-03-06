package com.trabalho.transacao_serivce.mapper;

import com.trabalho.transacao_serivce.database.entity.Transacao;
import com.trabalho.transacao_serivce.dto.request.TransacaoRequestDTO;
import com.trabalho.transacao_serivce.dto.response.TransacaoResponseDTO;

import java.time.LocalDateTime;

public final class TransacaoMapper {

    public static Transacao toTransacao(TransacaoRequestDTO request){
        return Transacao.builder()
                .tipoTransacao(request.getTipoTransacao())
                .valor(request.getValor())
                .idUsuario(request.getIdUsuario())
                .tipoConta(request.getTipoConta())
                .dataCriacao(LocalDateTime.now())
                .build();
    }

    public static TransacaoResponseDTO toResponse(Transacao transacao){
        return TransacaoResponseDTO.builder()
                .idTransacao(transacao.getIdTransacao())
                .idUsuario(transacao.getIdUsuario())
                .valor(transacao.getValor())
                .tipoTransacao(transacao.getTipoTransacao())
                .tipoConta(transacao.getTipoConta())
                .dataCriacao(transacao.getDataCriacao())
                .build();
    }
}
