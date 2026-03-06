package com.trabalho.transacao_serivce.dto.response;

import com.trabalho.transacao_serivce.database.entity.enums.TipoConta;
import com.trabalho.transacao_serivce.database.entity.enums.TipoTransacao;
import lombok.Builder;
import lombok.Getter;
import org.bson.types.ObjectId;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class TransacaoResponseDTO {
    private ObjectId idTransacao;
    private Long idUsuario;
    private BigDecimal valor;
    private LocalDateTime dataCriacao;
    private TipoTransacao tipoTransacao;
    private TipoConta tipoConta;
}
