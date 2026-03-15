package com.trabalho.transacao_serivce.database.oracle;


import com.trabalho.transacao_serivce.config.redis.RedisConfig;
import com.trabalho.transacao_serivce.database.oracle.model.ClienteSaldoModel;
import com.trabalho.transacao_serivce.database.oracle.repository.ClienteSaldoRepository;
import io.netty.util.collection.LongObjectHashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

import static com.trabalho.transacao_serivce.database.entity.enums.TipoConta.CREDITO;
import static com.trabalho.transacao_serivce.database.entity.enums.TipoConta.DEBITO;


@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClientrModelController {

    private final ClienteSaldoRepository repository;
    private final RedisTemplate<String,Object> redisTemplate;

    @PostMapping
    public void criaCliente(@RequestBody ClienteSaldoModel request,
                            HttpEntity<Object> httpEntity){
        ClienteSaldoModel entity = repository.save(request);
        String chave = "cliente" + entity.getIdUsuario();

        Long saldoCredito = entity.getSaldoCredito().longValue();
        Long saldoDebito = entity.getSaldoDebito().longValue();

        redisTemplate.opsForHash().put(chave,CREDITO.name(),saldoCredito);
        redisTemplate.opsForHash().put(chave,DEBITO.name(),saldoDebito);
    }

}
