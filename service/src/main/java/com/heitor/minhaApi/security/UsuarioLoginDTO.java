package com.heitor.minhaApi.security;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioLoginDTO {

    @NotEmpty(message = "Campo obrigatório")
    private String username;
    @NotEmpty(message = "Campo obrigatório")
    @Size(min = 6, max = 20, message = "Campo deve conter de 6 a 20 caracteres")
    private String password;
}
