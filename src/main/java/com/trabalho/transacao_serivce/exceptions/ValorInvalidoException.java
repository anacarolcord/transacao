package com.trabalho.transacao_serivce.exceptions;

public class ValorInvalidoException extends RuntimeException {
    public ValorInvalidoException() {
        super("A transação nao pode ser efetivada pois o valor é nulo/negativo");
    }
}
