package com.trabalho.transacao_serivce.exceptions;

public class ErroProcessarTransacaoExcepetion extends RuntimeException {


    public ErroProcessarTransacaoExcepetion() {
        super("Ocorreu um erro ao processar a transação");
    }
}
