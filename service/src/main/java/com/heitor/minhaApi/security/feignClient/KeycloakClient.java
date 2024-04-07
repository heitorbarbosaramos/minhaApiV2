package com.heitor.minhaApi.security.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "keycloakClient", url = "http://localhost:8090/auth")
public interface KeycloakClient {

    @PostMapping(value = "/realms/${keycloak.client.realm}/protocol/openid-connect/token", consumes = "application/x-www-form-urlencoded")
    TokenResponse getToken(@RequestHeader("Content-Type") String contentType, @RequestBody TokenRequest request);

    @PostMapping(value = "/realms/${keycloak.client.realm}/protocol/openid-connect/token", consumes = "application/x-www-form-urlencoded")
    TokenResponse refreshToken(@RequestHeader("Content-Type") String contentType, @RequestBody TokenRefreshRequest request);

    @GetMapping(value = "/realms/${keycloak.client.realm}/protocol/openid-connect/userinfo")
    UserInfoResponse getUserInfo(@RequestHeader("Authorization") String Authorization);

    @PostMapping(value = "/realms/${keycloak.client.realm}/protocol/openid-connect/token/introspect", consumes = "application/x-www-form-urlencoded")
    UserIntrospectResponse getUserIntrospect(@RequestHeader("Content-Type") String contentType, @RequestBody UserIntrospectRequest request);

    @PostMapping(value = "/admin/realms/${keycloak.client.realm}/users", consumes = MediaType.APPLICATION_JSON_VALUE)
    Object createUser(@RequestHeader("Authorization") String authorization, @RequestBody UserRepresentarioKeyCloak user);
}
