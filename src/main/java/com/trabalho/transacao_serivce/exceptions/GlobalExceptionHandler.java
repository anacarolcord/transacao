package com.trabalho.transacao_serivce.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ErroProcessarTransacaoExcepetion.class)
    public ResponseEntity<String> handleErroProcessoTransacao(ErroProcessarTransacaoExcepetion excepetion){
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(excepetion.getMessage());
    }

    @ExceptionHandler(TransacaoInvalidaException.class)
    public ResponseEntity<String> handleTransacaoInvalida(TransacaoInvalidaException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(ValorInvalidoException.class)
    public ResponseEntity<String> handleValorInvalido(ValorInvalidoException e){
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
    }

    @ExceptionHandler(SaldoInsuficienteException.class)
    public ResponseEntity<String> handleSaldoInsuficiente(SaldoInsuficienteException e){
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
    }


}
