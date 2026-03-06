package com.trabalho.transacao_serivce.dto.request;


import com.trabalho.transacao_serivce.database.entity.enums.TipoConta;
import com.trabalho.transacao_serivce.database.entity.enums.TipoTransacao;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class TransacaoRequestDTO {
    private Long idUsuario;
    private BigDecimal valor;
    private TipoTransacao tipoTransacao;
    private TipoConta tipoConta;
}
