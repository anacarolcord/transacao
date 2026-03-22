package com.trabalho.transacao_serivce.dto;

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
