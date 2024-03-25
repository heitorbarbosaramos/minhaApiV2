package com.heitor.minhaApi.security.feignClient;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenRefreshRequest {

    private String client_id;
    private String client_secret;
    private String refresh_token;
    private String grant_type;
}
