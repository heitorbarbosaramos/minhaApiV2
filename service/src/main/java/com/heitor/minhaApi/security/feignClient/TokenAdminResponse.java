package com.heitor.minhaApi.security.feignClient;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
public class TokenAdminResponse {

    private String access_token;
    private Integer expires_in;
    private Integer refresh_expires_in;
    private String token_type;
    @JsonProperty(value = "not-before-policy")
    private Integer not_before_policy;
    private String scope;
}
