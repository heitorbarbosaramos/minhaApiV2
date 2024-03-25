package com.heitor.minhaApi.security.feignClient;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenRequest {

    private String client_id;
    private String client_secret;
    private String grant_type;
    private String username;
    private String password;
}
