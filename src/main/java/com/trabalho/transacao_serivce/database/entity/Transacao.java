package com.trabalho.transacao_serivce.database.entity;


import com.trabalho.transacao_serivce.database.entity.enums.TipoConta;
import com.trabalho.transacao_serivce.database.entity.enums.TipoTransacao;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class Transacao {
    @Id
    private ObjectId idTransacao;
    private Long idUsuario;
    private BigDecimal valor;
    private LocalDateTime dataCriacao;
    private TipoTransacao tipoTransacao;
    private TipoConta tipoConta;
}
