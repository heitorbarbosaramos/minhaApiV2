package com.heitor.minhaApi.security.feignClient;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserIntrospectRequest {

    private String client_id;
    private String client_secret;
    private String token;
}
