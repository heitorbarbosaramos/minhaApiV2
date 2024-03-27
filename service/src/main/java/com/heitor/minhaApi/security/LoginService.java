package com.heitor.minhaApi.security;

import com.heitor.minhaApi.security.feignClient.*;
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


    public ResponseEntity<?> login(UsuarioLoginDTO loginDTO){

        TokenRequest request = new TokenRequest();
        request.setClient_id(clientId);
        request.setClient_secret(clientSecret);
        request.setGrant_type(grantType);
        request.setUsername(loginDTO.getUsername());
        request.setPassword(loginDTO.getPassword());

        TokenResponse response = keycloakClient.getToken("application/x-www-form-urlencoded", request);

        UserInfoResponse userInfo = keycloakClient.getUserInfo("Bearer " + response.getAccess_token());

        UserIntrospectResponse responserUser = userIntrospectResponse(response.getAccess_token());


        return ResponseEntity.ok(responserUser);
    }

    public ResponseEntity<?> refreshToken(String refreshToken){

        TokenRefreshRequest request = new TokenRefreshRequest();
        request.setClient_id(clientId);
        request.setClient_secret(clientSecret);
        request.setGrant_type("refresh_token");
        request.setRefresh_token(refreshToken);

        TokenResponse response = keycloakClient.refreshToken("application/x-www-form-urlencoded", request);

        UserInfoResponse userInfo = keycloakClient.getUserInfo("Bearer " + response.getAccess_token());
        return ResponseEntity.ok(userInfo);
    }

    public UserIntrospectResponse userIntrospectResponse(String token){

        UserIntrospectRequest requestUser = new UserIntrospectRequest();
        requestUser.setClient_id(clientId);
        requestUser.setClient_secret(clientSecret);
        requestUser.setToken(token);

        UserIntrospectResponse responserUser = keycloakClient.getUserIntrospect("application/x-www-form-urlencoded", requestUser);

        return responserUser;
    }
}
