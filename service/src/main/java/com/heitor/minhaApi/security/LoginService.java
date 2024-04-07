package com.heitor.minhaApi.security;

import com.heitor.minhaApi.security.feignClient.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final KeycloakClient keycloakClient;
    private final CookiesUtils cookiesUtils;

    @Value("${keycloak.client.id}")
    private String clientId;
    @Value("${keycloak.client.secret}")
    private String clientSecret;
    @Value("${keycloak.client.grantype}")
    private String grantType;


    public ResponseEntity<?> login(HttpServletRequest request, HttpServletResponse response, UsuarioLoginDTO loginDTO){

        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.setClient_id(clientId);
        tokenRequest.setClient_secret(clientSecret);
        tokenRequest.setGrant_type(grantType);
        tokenRequest.setUsername(loginDTO.getUsername());
        tokenRequest.setPassword(loginDTO.getPassword());

        TokenResponse tokenResponse = keycloakClient.getToken("application/x-www-form-urlencoded", tokenRequest);

        UserInfoResponse userInfo = keycloakClient.getUserInfo("Bearer " + tokenResponse.getAccess_token());

        UserIntrospectResponse responserUser = userIntrospectResponse(tokenResponse.getAccess_token());

        cookiesUtils.createCookieToken(request, response, tokenResponse.getAccess_token());
        cookiesUtils.createCookiePerfil(request, response, responserUser.getRealm_access().getRoles());


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

    public void createUser(UserRepresentarioKeyCloak user, HttpServletRequest request, HttpServletResponse response){
        String token = request.getHeader("Authorization");
        keycloakClient.createUser(token, user);
    }
}
