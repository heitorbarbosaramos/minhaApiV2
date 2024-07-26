package com.heitor.minhaApi.security.dto;

import com.heitor.minhaApi.security.enums.ActivationMethod;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    @Size(min = 10, max = 11, message = "O campo deve conter entre 10 e 11 caracteres")
    private String telefone;
    @NotEmpty(message = "Campo obrigatório")
    @Email(message = "Campo deve ser um email")
    private String email;
    @NotNull(message = "Campo obrigatório")
    private ActivationMethod metodoAtivacao;
}
