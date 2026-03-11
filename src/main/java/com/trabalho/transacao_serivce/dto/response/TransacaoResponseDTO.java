package com.trabalho.transacao_serivce.dto.response;

import com.trabalho.transacao_serivce.database.entity.enums.StatusTransacao;
import com.trabalho.transacao_serivce.database.entity.enums.TipoConta;
import com.trabalho.transacao_serivce.database.entity.enums.TipoTransacao;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class TransacaoResponseDTO {
    private UUID idTransacao;
    private Long idUsuario;
    private BigDecimal valor;
    private LocalDateTime dataCriacao;
    private TipoTransacao tipoTransacao;
    private TipoConta tipoConta;
    private StatusTransacao statusTransacao;
}
