package com.trabalho.transacao_serivce.exceptions;

public class ClienteNaoEncontradoException extends RuntimeException {

    public ClienteNaoEncontradoException(){
        super("Cliente não existe na base de dados!");
    }
}
