package com.trabalho.transacao_serivce.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trabalho.transacao_serivce.database.entity.enums.StatusTransacao;
import com.trabalho.transacao_serivce.database.entity.enums.TipoConta;
import com.trabalho.transacao_serivce.database.oracle.model.ClienteSaldoModel;
import com.trabalho.transacao_serivce.database.oracle.repository.ClienteSaldoRepository;
import com.trabalho.transacao_serivce.dto.ClienteDTO;
import com.trabalho.transacao_serivce.dto.response.TransacaoSaldoStatusDTO;
import com.trabalho.transacao_serivce.exceptions.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.propertyeditors.CurrencyEditor;
import org.springframework.data.redis.connection.stream.StreamInfo;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

import static com.trabalho.transacao_serivce.database.entity.enums.TipoConta.CREDITO;
import static com.trabalho.transacao_serivce.database.entity.enums.TipoConta.DEBITO;
import static com.trabalho.transacao_serivce.mapper.TransacaoMapper.toClienteDTO;

@Component
@RequiredArgsConstructor
public class RedisUtils {

    private final RedisTemplate<String,String> redisTemplate;
    private final ObjectMapper objectMapper;
    private final ClienteSaldoRepository clienteSaldoRepository;


    public TransacaoSaldoStatusDTO processarTransacao(Long idUsuario, BigDecimal valor, TipoConta tipoContaEnum){
        String chave = String.valueOf(idUsuario);

         Optional<ClienteDTO> clienteDTO = consultaClienteRedis(idUsuario,chave, tipoContaEnum.name(), valor);

        if (clienteDTO.isEmpty()) {
            throw new ClienteNaoEncontradoException();
        }

        ClienteDTO cliente = clienteDTO.get();

        return decrement(chave,cliente.getIdUsuario(),valor,tipoContaEnum);
    }

    public TransacaoSaldoStatusDTO decrement(String chave, Long idUsuario, BigDecimal valor, TipoConta tipoContaEnum){
        TransacaoSaldoStatusDTO response = new TransacaoSaldoStatusDTO();

        String tipoConta = tipoContaEnum.name();

        if(isValidTransacao(chave,valor, tipoConta, response)){

            Long centavos = valor.movePointRight(2).longValue();
            Long saldoPosDecremento = redisTemplate.opsForHash()
                    .increment(chave,tipoConta,-centavos);

            BigDecimal saldoEmReaisAtualizado = BigDecimal.valueOf(saldoPosDecremento).movePointLeft(2);

            if(saldoEmReaisAtualizado.compareTo(BigDecimal.ZERO) < 0){
                Long saldoAtual = redisTemplate.opsForHash().increment(chave,tipoConta,centavos);
                response.setStatusTransacao(StatusTransacao.REPROVADA);
                response.setSaldoAtualizado(BigDecimal.valueOf(saldoAtual).movePointLeft(2));

            }else{
                response.setStatusTransacao(StatusTransacao.APROVADA);
                response.setSaldoAtualizado(saldoEmReaisAtualizado);
            }

        }else{
            throw new TransacaoInvalidaException();
        }
        return response;
    }

    public boolean isValidTransacao(String chave, BigDecimal valor, String tipoConta, TransacaoSaldoStatusDTO response){

        if(valor.compareTo(BigDecimal.ZERO) == 0 || valor.compareTo(BigDecimal.ZERO) < 0){
            throw new ValorInvalidoException();
        }else {
            return true;

        }

    }


    @Transactional
    public Optional<ClienteDTO> consultaClienteRedis(Long idUsuario,String chave, String tipoConta, BigDecimal valor) {

        Object valorNoRedis = redisTemplate.opsForHash().get(chave, tipoConta);

        if(Objects.equals(valorNoRedis,"0")){
            throw new SaldoInsuficienteException();
        }

        if (Objects.nonNull(valorNoRedis)  ) {
            String dadoRedis = String.valueOf(valorNoRedis);
            ClienteDTO clienteDTO = objectMapper.convertValue(dadoRedis,ClienteDTO.class);
            return Optional.of(clienteDTO);
        }

        ClienteDTO clienteDoBanco = buscaDadosNoBanco(idUsuario);

        if (Objects.isNull(valorNoRedis)) {
            populaDadosNoRedis(clienteDoBanco, chave);
        }

        return Optional.ofNullable(clienteDoBanco);
    }

        public void populaDadosNoRedis(ClienteDTO cliente, String chave) {

            Long credito = cliente.getSaldoCredito().movePointRight(2).longValue();
            Long debito = cliente.getSaldoDebito().movePointRight(2).longValue();

            redisTemplate.opsForHash().put(chave,DEBITO.name(),String.valueOf(debito));
            redisTemplate.opsForHash().put(chave,CREDITO.name(), String.valueOf(credito));
        }


        public ClienteDTO buscaDadosNoBanco(Long idUsuario){

            ClienteSaldoModel clienteSaldoModel = clienteSaldoRepository.findById(idUsuario)
                    .orElseThrow();

            return toClienteDTO(clienteSaldoModel);
        }

    }










