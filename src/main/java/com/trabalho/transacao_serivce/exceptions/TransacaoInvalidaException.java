package com.trabalho.transacao_serivce.exceptions;

public class TransacaoInvalidaException extends RuntimeException {
    public TransacaoInvalidaException() {
        super("A transação é inválida, usuário nao encontrado ou valor incompatível com saldo atual");
    }
}
