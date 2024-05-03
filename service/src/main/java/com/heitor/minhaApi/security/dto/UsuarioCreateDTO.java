package com.heitor.minhaApi.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioCreateDTO {

    @NotEmpty(message = "Campo obrigatório")
    private String nome;
    @NotEmpty(message = "Campo obrigatório")
    private String sobreNome;
    @NotEmpty(message = "Campo obrigatório")
    @Size(min = 14, max = 15, message = "O campo deve conter entre 14 e 15 caracteres")
    private String telefone;
    @NotEmpty(message = "Campo obrigatório")
    @Email(message = "Campo deve ser um email")
    private String email;
}
