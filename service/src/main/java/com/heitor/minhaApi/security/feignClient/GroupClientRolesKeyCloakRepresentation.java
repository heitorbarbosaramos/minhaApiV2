package com.heitor.minhaApi.security.feignClient;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GroupClientRolesKeyCloakRepresentation {

    @JsonProperty(value = "realm-management")
    private List<String> realm_management;
    @JsonProperty(value = "CLIENT_MINHA_API")
    private List<String> CLIENT_MINHA_API;
    private List<String> broker;
    private List<String> account;
}
