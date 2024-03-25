package com.heitor.minhaApi.security.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "keycloakClient", url = "http://localhost:8090/auth")
public interface KeycloakClient {

    @PostMapping(value = "/realms/REALM_MINHAAPI/protocol/openid-connect/token", consumes = "application/x-www-form-urlencoded")
    TokenResponse getToken(@RequestHeader("Content-Type") String contentType, @RequestBody TokenRequest request);
}
