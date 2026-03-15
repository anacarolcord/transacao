package com.trabalho.transacao_serivce.mapper;

import com.trabalho.transacao_serivce.database.entity.enums.StatusTransacao;
import com.trabalho.transacao_serivce.database.entity.Transacao;
import com.trabalho.transacao_serivce.dto.request.TransacaoRequestDTO;
import com.trabalho.transacao_serivce.dto.response.TransacaoResponseDTO;
import com.trabalho.transacao_serivce.dto.response.TransacaoSaldoStatusDTO;

import java.time.LocalDateTime;
import java.util.UUID;

public final class TransacaoMapper {


    public static TransacaoResponseDTO toResponse(TransacaoRequestDTO request, TransacaoSaldoStatusDTO info){
        TransacaoResponseDTO response = new TransacaoResponseDTO();

        response.setDataCriacao(LocalDateTime.now());
        response.setIdTransacao(UUID.randomUUID());
        response.setIdUsuario(request.getIdUsuario());
        response.setTipoConta(request.getTipoConta());
        response.setTipoTransacao(request.getTipoTransacao());
        response.setStatusTransacao(info.getStatusTransacao());
        response.setSaldoAtualizado(info.getSaldoAtualizado());

        return response;
    }
}
