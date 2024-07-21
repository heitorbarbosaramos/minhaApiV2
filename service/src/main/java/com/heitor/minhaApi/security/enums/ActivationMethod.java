package com.heitor.minhaApi.security.enums;

import lombok.Getter;

@Getter
public enum ActivationMethod {

    EMAIL(1, "Email"),
    WHATSAPP(2, "Whatsapp");

    final int cod;
    final String descricao;

    ActivationMethod(int cod, String descricao){
        this.cod = cod;
        this.descricao = descricao;
    }

}
