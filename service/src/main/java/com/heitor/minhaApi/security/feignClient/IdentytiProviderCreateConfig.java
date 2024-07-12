package com.heitor.minhaApi.security.feignClient;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IdentytiProviderCreateConfig {

    @NotEmpty(message = "Campo Obrigatório")
    private String clientId;
    @NotEmpty(message = "Campo Obrigatório")
    private String clientSecret;
    private String guiOrder;
    private String hostedDomain;
    private boolean offlineAccess;
    private boolean userIp;
}
