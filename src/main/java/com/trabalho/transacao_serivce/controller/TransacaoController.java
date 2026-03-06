package com.trabalho.transacao_serivce.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transacao")
public class TransacaoController {

    @PostMapping
    public ResponseEntity<TransacaoResponseDTO> saveTransacao(@RequestBody TransacaoRequestDTO){
        return ResponseEntity.ok(response);
    }

}
