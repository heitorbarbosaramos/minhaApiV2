package com.heitor.minhaApi.enums;

import lombok.Getter;

public enum UsuarioStatus {
    ATIVACAO(1, "Esperando confirmação"),
    ATIVADO_EMAIL(2, "Confirmado via email"),
    ATIVADO_SMS(3, "Confirmado via SMS"),
    ATIVADO_REDE_SOCIAL(4, "Confirmado via redes sociais");

    @Getter
    final Integer cod;
    @Getter
    final String descricao;

    UsuarioStatus(Integer cod, String descricao){
        this.cod = cod;
        this.descricao = descricao;
    }
}
