package com.trabalho.transacao_serivce.dto.request;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.trabalho.transacao_serivce.database.entity.enums.TipoConta;
import com.trabalho.transacao_serivce.database.entity.enums.TipoTransacao;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.math.BigDecimal;

@Getter
@Builder
public class TransacaoRequestDTO {
    @NonNull
    private Long idUsuario;
    private BigDecimal valor;
    @NonNull
    private TipoTransacao tipoTransacao;
    @NonNull
    private TipoConta tipoConta;
}
