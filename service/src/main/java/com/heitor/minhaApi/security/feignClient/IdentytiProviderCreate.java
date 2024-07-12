package com.heitor.minhaApi.security.feignClient;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IdentytiProviderCreate {

    @NotEmpty(message = "Campo Obrigatório")
    private String alias;
    @NotEmpty(message = "Campo Obrigatório")
    private String providerId;
    @NotNull(message = "Campo Obrigatório")
    private IdentytiProviderCreateConfig config;
}
