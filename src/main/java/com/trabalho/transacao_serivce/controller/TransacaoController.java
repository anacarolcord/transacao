package com.trabalho.transacao_serivce.controller;

import com.trabalho.transacao_serivce.dto.request.TransacaoRequestDTO;
import com.trabalho.transacao_serivce.dto.response.TransacaoResponseDTO;
import com.trabalho.transacao_serivce.service.TransacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transacao")
public class TransacaoController {

    private final TransacaoService transacaoService;

    @PostMapping
    public ResponseEntity<TransacaoResponseDTO> saveTransacao(@RequestBody TransacaoRequestDTO request){

        TransacaoResponseDTO response = transacaoService.processaTransacao(request);

        return ResponseEntity.ok(response);
    }

}
