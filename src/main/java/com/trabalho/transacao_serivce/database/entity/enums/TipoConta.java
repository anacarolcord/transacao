package com.trabalho.transacao_serivce.database.entity.enums;

import lombok.Getter;

@Getter
public enum TipoConta {
    CREDITO("creditoStrategy"),
    DEBITO("debitoStrategy");

    private final String nomeBeanConta;

    TipoConta(String nomeBeanConta) {
        this.nomeBeanConta = nomeBeanConta;
    }
}
