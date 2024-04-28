package com.heitor.minhaApi.enums;

public enum UsuarioStatus {
    ATIVACAO(1, "Esperando confirmação"),
    ATIVADO_EMAIL(2, "Confirmado via email"),
    ATIVADO_SMS(3, "Confirmado via SMS");

    UsuarioStatus(Integer cod, String descricao){
    }

    public Integer getCod(){
        return this.getCod();
    }

    public String getNome(){
        return this.getNome();
    }

    public String getDescricao(){
        return this.getDescricao();
    }
}
