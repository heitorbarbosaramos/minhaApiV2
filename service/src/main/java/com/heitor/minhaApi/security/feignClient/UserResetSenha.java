package com.heitor.minhaApi.security.feignClient;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResetSenha {

    private Boolean temporary;
    private String type;
    private String value;
}
