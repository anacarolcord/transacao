package com.trabalho.transacao_serivce.database.entity.enums;

import lombok.Getter;

@Getter
public enum TipoTransacao {
    ENTRADA("entradaStrategy"),
    SAIDA("saidaStrategy");

    private final String nomeDoBeanTransacao;

    TipoTransacao(String nomeDoBeanTransacao){
        this.nomeDoBeanTransacao = nomeDoBeanTransacao;
    }
}
