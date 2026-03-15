package com.trabalho.transacao_serivce.database.oracle.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class ClienteSaldoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;
    @Column(name = "SALDO_CREDITO")
    private BigDecimal saldoCredito;
    @Column(name = "SALDO_DEBITO")
    private BigDecimal saldoDebito;

}
