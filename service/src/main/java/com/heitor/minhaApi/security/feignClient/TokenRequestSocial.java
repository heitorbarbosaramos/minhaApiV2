package com.heitor.minhaApi.security.feignClient;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenRequestSocial {

    private String grant_type;
    private String code;
    private String redirect_uri;
    private String client_id;
    private String client_secret;
}
