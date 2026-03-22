package com.trabalho.transacao_serivce.dto;

import com.trabalho.transacao_serivce.database.entity.enums.StatusTransacao;
import jdk.jshell.Snippet;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ClienteDTO {

    private Long idUsuario;
    private BigDecimal saldoCredito;
    private BigDecimal saldoDebito;
}
