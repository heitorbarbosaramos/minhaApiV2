package com.heitor.minhaApi.security;

import com.heitor.minhaApi.security.feignClient.*;
import com.heitor.minhaApi.security.utils.TokenUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    @Value("${keycloak.client.realm}")
    private String realm;
    @Value("${keycloak.client.adminUsuario}")
    private String userAdminKeycloak;
    @Value("${keycloak.client.adminSenha}")
    private String userAdminKeycloakPassword;

    private TokenResponse tokenResponse(TokenRequest tokenRequest){
        return keycloakClient.getToken("application/x-www-form-urlencoded", tokenRequest);
    }

    private TokenAdminResponse getTokenAdmin(){

        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.setClient_id(clientId);
        tokenRequest.setClient_secret(clientSecret);
        tokenRequest.setGrant_type("client_credentials");

        return keycloakClient.getTokenAdmin("application/x-www-form-urlencoded",tokenRequest);
    }

    public ResponseEntity<?> login(HttpServletRequest request, HttpServletResponse response, UsuarioLoginDTO loginDTO){

        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.setClient_id(clientId);
        tokenRequest.setClient_secret(clientSecret);
        tokenRequest.setGrant_type(grantType);
        tokenRequest.setUsername(loginDTO.getUsername());
        tokenRequest.setPassword(loginDTO.getPassword());

        TokenResponse tokenResponse = tokenResponse(tokenRequest);

        UserIntrospectResponse responserUser = userIntrospectResponse(tokenResponse.getAccess_token());

        List<String > roles = new ArrayList<>();

        if(responserUser.getRealm_access() != null){
            roles.addAll(responserUser.getRealm_access().getRoles());
        }

        cookiesUtils.createCookieToken(request, response, tokenResponse.getAccess_token());
        cookiesUtils.createCookieTokenRefresh(request, response, tokenResponse.getRefresh_token());
        cookiesUtils.createCookiePerfil(request, response, roles);

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

    public UserRepresentarioKeyCloak createUser(UserRepresentarioKeyCloak user, HttpServletRequest request, HttpServletResponse response){
        String token = TokenUtils.RetrieveToken(request);
        if(token == null){
            TokenRequest tokenRequest = new TokenRequest();
            tokenRequest.setClient_id(clientId);
            tokenRequest.setClient_secret(clientSecret);
            tokenRequest.setGrant_type(grantType);
            tokenRequest.setUsername(userAdminKeycloak);
            tokenRequest.setPassword(userAdminKeycloakPassword);

            TokenResponse tokenResponse = tokenResponse(tokenRequest);
            token = "Bearer " + tokenResponse.getAccess_token();
        }
        keycloakClient.createUser(token, user);
        return keycloakClient.findByUserName(token, user.getUsername()).stream().findFirst().get();
    }

    public List<UserRepresentarioKeyCloak> findByUserName(String userName, HttpServletRequest request, HttpServletResponse response){
        String token = TokenUtils.RetrieveToken(request);
        return keycloakClient.findByUserName(token, userName);
    }

    public List<UserRepresentarioKeyCloak> findAllUser(HttpServletRequest request, HttpServletResponse response){
        String token = TokenUtils.RetrieveToken(request);
        return keycloakClient.findAllUser(token);
    }

    public void updateUser(String userId, UserRepresentarioKeyCloak user, HttpServletRequest request, HttpServletResponse response){
        String token = TokenUtils.RetrieveToken(request);
        keycloakClient.updateUser(token, userId, user);
    }

    public void resetSenha(String userId, UserResetSenha rest, HttpServletRequest request, HttpServletResponse response ){
        String token = TokenUtils.RetrieveToken(request);
        UserInfoResponse userInfo = keycloakClient.getUserInfo(token);

        if(Objects.equals(userInfo.getSub(), userId)){
            TokenRequest tokenRequest = new TokenRequest();
            tokenRequest.setClient_id(clientId);
            tokenRequest.setClient_secret(clientSecret);
            tokenRequest.setGrant_type(grantType);
            tokenRequest.setUsername(userAdminKeycloak);
            tokenRequest.setPassword(userAdminKeycloakPassword);

            TokenResponse tokenResponse = tokenResponse(tokenRequest);
            token = "Bearer " + tokenResponse.getAccess_token();
        }
        keycloakClient.resetPassWord(token, userId, rest);
    }
}
