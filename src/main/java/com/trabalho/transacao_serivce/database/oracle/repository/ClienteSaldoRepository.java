package com.trabalho.transacao_serivce.database.oracle.repository;

import com.trabalho.transacao_serivce.database.oracle.model.ClienteSaldoModel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface ClienteSaldoRepository extends JpaRepository<Long,ClienteSaldoModel> {

    public ClienteSaldoModel save(ClienteSaldoModel cliente);

    @Modifying
    @Transactional
    @Query("UPDATE ClienteSaldoModel c SET c.saldoCredito = :valorAtualizado WHERE c.idUsuario = :idUsuario")
    public ClienteSaldoModel updateSaldoCredito(@Param("idUsuario")Long idUsuario,
                                                @Param("valorAtualizado")BigDecimal valorAtualizado);

    @Modifying
    @Transactional
    @Query("UPDATE ClienteSaldoModel c SET c.saldoDebito = :valorAtualizado WHERE c.idUsuario = :idUsuario")
    public ClienteSaldoModel updateSaldoDebito(@Param("idUsuario")Long idUsuario,
                                               @Param("valorAtualizado")BigDecimal valorAtualizado);

}
