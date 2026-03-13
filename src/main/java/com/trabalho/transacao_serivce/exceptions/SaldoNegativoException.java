package com.trabalho.transacao_serivce.exceptions;

public class SaldoNegativoException extends RuntimeException {
    public SaldoNegativoException() {
        super("Transacao negada pois o saldo disponivel ficaria negativo!");
    }
}
