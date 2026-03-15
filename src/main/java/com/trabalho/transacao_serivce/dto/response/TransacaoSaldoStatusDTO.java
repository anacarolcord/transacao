package com.trabalho.transacao_serivce.dto.response;

import com.trabalho.transacao_serivce.database.entity.enums.StatusTransacao;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@RequiredArgsConstructor
public class TransacaoSaldoStatusDTO {
    private BigDecimal saldoAtualizado;
    private StatusTransacao statusTransacao;
}
