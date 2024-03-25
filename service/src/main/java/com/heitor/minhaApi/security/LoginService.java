package com.heitor.minhaApi.security;

import com.heitor.minhaApi.security.feignClient.KeycloakClient;
import com.heitor.minhaApi.security.feignClient.TokenRequest;
import com.heitor.minhaApi.security.feignClient.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final KeycloakClient keycloakClient;

    @Value("${keycloak.client.id}")
    private String clientId;
    @Value("${keycloak.client.secret}")
    private String clientSecret;
    @Value("${keycloak.client.grantype}")
    private String grantType;


    public ResponseEntity<TokenResponse> login(UsuarioLoginDTO loginDTO){

        TokenRequest request = new TokenRequest();
        request.setClient_id(clientId);
        request.setClient_secret(clientSecret);
        request.setGrant_type(grantType);
        request.setUsername(loginDTO.getUsername());
        request.setPassword(loginDTO.getPassword());

        TokenResponse response = keycloakClient.getToken("application/x-www-form-urlencoded", request);

        return ResponseEntity.ok(response);
    }
}
