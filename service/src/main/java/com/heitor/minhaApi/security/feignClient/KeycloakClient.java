package com.heitor.minhaApi.security.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "keycloakClient", url = "${keycloak.client.server}")
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
    void createUser(@RequestHeader("Authorization") String authorization, @RequestBody UserRepresentarioKeyCloak user);

    @PutMapping(value = "/admin/realms/${keycloak.client.realm}/users/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    void updateUser(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable("userId") String userId,
            @RequestBody UserRepresentarioKeyCloak user
    );

    @GetMapping(value = "/admin/realms/${keycloak.client.realm}/users?username={userName}")
    List<UserRepresentarioKeyCloak> findByUserName(@RequestHeader("Authorization") String authorizationHeader, @PathVariable("userName") String userName);

    @GetMapping(value = "/admin/realms/${keycloak.client.realm}/users", consumes = MediaType.APPLICATION_JSON_VALUE)
    List<UserRepresentarioKeyCloak> findAllUser(@RequestHeader("Authorization") String authorizationHeader);

    @GetMapping(value = "/admin/realms/${keycloak.client.realm}/roles", consumes = MediaType.APPLICATION_JSON_VALUE)
    List<RolesRepresentationKeycloak> rolesKeycloak(@RequestHeader("Authorization") String autthorizationHeader);

    @GetMapping(value = "/admin/realms/${keycloak.client.realm}/roles/{roleName}", consumes = MediaType.APPLICATION_JSON_VALUE)
    RolesRepresentationKeycloak findByName(@RequestHeader("Authorization") String autthorizationHeader, @PathVariable("roleName") String roleName);
}
