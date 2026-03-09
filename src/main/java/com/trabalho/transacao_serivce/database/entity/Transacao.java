package com.trabalho.transacao_serivce.database.entity;


import com.trabalho.transacao_serivce.database.entity.enums.TipoConta;
import com.trabalho.transacao_serivce.database.entity.enums.TipoTransacao;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@Document(collection = "faturas")
public class Transacao {
    @Id
    private UUID idTransacao;
    private Long idUsuario;
    private BigDecimal valor;
    private LocalDateTime dataCriacao;
    private TipoTransacao tipoTransacao;
    private TipoConta tipoConta;
    private StatusTransacao statusTransacao;
}
