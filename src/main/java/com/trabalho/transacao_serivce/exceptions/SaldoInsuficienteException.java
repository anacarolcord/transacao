package com.trabalho.transacao_serivce.exceptions;

public class SaldoInsuficienteException extends RuntimeException {
    public SaldoInsuficienteException() {
        super("Saldo insuficiente");
    }
}
