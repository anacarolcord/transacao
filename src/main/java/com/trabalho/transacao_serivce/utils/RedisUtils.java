package com.trabalho.transacao_serivce.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trabalho.transacao_serivce.database.entity.enums.StatusTransacao;
import com.trabalho.transacao_serivce.database.entity.enums.TipoConta;
import com.trabalho.transacao_serivce.database.oracle.model.ClienteSaldoModel;
import com.trabalho.transacao_serivce.database.oracle.repository.ClienteSaldoRepository;
import com.trabalho.transacao_serivce.dto.ClienteDTO;
import com.trabalho.transacao_serivce.dto.response.TransacaoSaldoStatusDTO;
import com.trabalho.transacao_serivce.exceptions.ErroProcessarTransacaoExcepetion;
import com.trabalho.transacao_serivce.exceptions.TransacaoInvalidaException;
import com.trabalho.transacao_serivce.exceptions.ValorInvalidoException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.propertyeditors.CurrencyEditor;
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
    private final ClienteSaldoRepository clienteSaldoRepository;

    public TransacaoSaldoStatusDTO decrement(Long idUsuario, BigDecimal valor, TipoConta tipoContaEnum){
        TransacaoSaldoStatusDTO response = new TransacaoSaldoStatusDTO();

        String chave = "cliente" + idUsuario;
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

    public Optional<ClienteDTO> consultaClienteRedis(Long idUsuario,String chave, String tipoConta, BigDecimal valor) {

        Object valorNoRedis = redisTemplate.opsForHash().get(chave, tipoConta);
        ClienteDTO cliente = null;

        if (Objects.isNull(valorNoRedis)) {

            try {
                 cliente = buscaDadosNoBanco(idUsuario);

            } catch (RuntimeException e) {
                throw new EntityNotFoundException(e);
            }

        }

        return Optional.ofNullable(cliente);
    }

        public void populaDadosNoRedis(ClienteSaldoModel cliente) {
            String chave = String.valueOf(cliente.getIdUsuario());
            redisTemplate.opsForHash().put(chave,DEBITO.name(),cliente.getSaldoDebito());
            redisTemplate.opsForHash().put(chave,CREDITO.name(), cliente.getSaldoCredito());

        }


        public ClienteDTO buscaDadosNoBanco(Long idUsuario){

            ClienteSaldoModel clienteSaldoModel = clienteSaldoRepository.findById(idUsuario)
                    .orElseThrow();

            return toClienteDTO(clienteSaldoModel);
        }
    }








