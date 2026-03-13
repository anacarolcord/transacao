package com.trabalho.transacao_serivce.database.oracle.repository;

import com.trabalho.transacao_serivce.database.oracle.model.ClienteSaldoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface ClienteSaldoRepository extends JpaRepository<ClienteSaldoModel,Long> {

    public ClienteSaldoModel saveCliente(ClienteSaldoModel cliente);
    public ClienteSaldoModel updateSaldoCredito(BigDecimal valorAtualizado);
    public ClienteSaldoModel updateSaldoDebito(BigDecimal valorAtualizado);

}
