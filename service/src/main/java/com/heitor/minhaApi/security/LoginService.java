package com.heitor.minhaApi.security;

import com.heitor.minhaApi.entity.IdentytiProviderSSO;
import com.heitor.minhaApi.entity.Usuario;
import com.heitor.minhaApi.enums.UsuarioStatus;
import com.heitor.minhaApi.repostirory.UsuarioRepository;
import com.heitor.minhaApi.security.feignClient.*;
import com.heitor.minhaApi.security.utils.TokenUtils;
import com.heitor.minhaApi.service.IdentytiProviderSSOService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final KeycloakClient keycloakClient;
    private final CookiesUtils cookiesUtils;
    private final UsuarioRepository usuarioRepository;
    private final IdentytiProviderSSOService providerSSOService;

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
    @Value("${bucket.front.url}")
    private String bucketFront;
    @Value("${keycloak.client.server}")
    private String keycloakClientServer;
    @Value("${keycloak.client.uri.redirect}")
    private String uriRedirect;

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

    public void createIdentytiProvider(IdentytiProviderCreate create, HttpServletRequest request, HttpServletResponse response){
        String token = TokenUtils.RetrieveToken(request);
        keycloakClient.createIdentytiProvider(token, create);

        IdentytiProviderSSO sso = new IdentytiProviderSSO();
        sso.setAlias(create.getAlias());
        sso.setProviderId(create.getProviderId());
        providerSSOService.save(sso);
    }

    public List<IdentytiProvider> findAllIdentytiProvider(HttpServletRequest request, HttpServletResponse response){
        String token = TokenUtils.RetrieveToken(request);
        return keycloakClient.findAllIdentytiProvider(token);
    }

    public HashMap<String, String> loginSocial(){
        HashMap<String, String> list = new HashMap<>();
        list.put("geral", keycloakClientServer+"/realms/"+realm+"/protocol/openid-connect/auth?client_id="+clientId+"&redirect_uri="+uriRedirect+"&response_type=code&scope=openid");

        List<IdentytiProviderSSO> findAllProviders = providerSSOService.findAll();

        for(IdentytiProviderSSO x : findAllProviders){
            list.put(x.getAlias(), keycloakClientServer+"/realms/"+realm+"/protocol/openid-connect/auth?client_id="+clientId+"&redirect_uri="+uriRedirect+"&response_type=code&scope=openid&kc_idp_hint="+x.getAlias());
        }
        return list;
    }

    public ResponseEntity<?> trocaCode(HttpServletRequest request, HttpServletResponse response,String code, String sesseioState){

        TokenRequestSocial social = new TokenRequestSocial();
        social.setGrant_type("authorization_code");
        social.setCode(code);
        social.setRedirect_uri(uriRedirect);
        social.setClient_id(clientId);
        social.setClient_secret(clientSecret);

        TokenResponse tokenResponse = keycloakClient.loginSocialCode(social);

        return login(request, response, null, tokenResponse);
    }

    public ResponseEntity<?> login(HttpServletRequest request, HttpServletResponse response, UsuarioLoginDTO loginDTO, TokenResponse tokenResponseParam){

        TokenRequest tokenRequest = new TokenRequest();
        if(loginDTO != null) {
            tokenRequest.setClient_id(clientId);
            tokenRequest.setClient_secret(clientSecret);
            tokenRequest.setGrant_type(grantType);
            tokenRequest.setUsername(loginDTO.getUsername());
            tokenRequest.setPassword(loginDTO.getPassword());
        }

        TokenResponse tokenResponse = tokenResponseParam == null ? tokenResponse(tokenRequest) : tokenResponseParam;

        UserIntrospectResponse responserUser = userIntrospectResponse(tokenResponse.getAccess_token());

        if(loginDTO == null){
            Usuario usuario = usuarioRepository.findByIdUsuarioKeycloak(UUID.fromString(responserUser.getSub()));
            if(usuario == null) {
                usuario = new Usuario();
                usuario.setIdUsuarioKeycloak(UUID.fromString(responserUser.getSub()));
                usuario.setNome(responserUser.getGiven_name());
                usuario.setSobreNome(responserUser.getFamily_name());
                usuario.setEmail(responserUser.getEmail());
                usuario.setStatus(UsuarioStatus.ATIVADO_REDE_SOCIAL);
                usuarioRepository.save(usuario);
            }
        }

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

    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String token = TokenUtils.RetrieveToken(request);
        keycloakClient.logout(token);
        cookiesUtils.revokeCookies(request, response);
        response.sendRedirect(bucketFront);
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

    public List<UserRepresentarioKeyCloak> findAllUser(String briefRepresentation, Integer first, Integer max, HttpServletRequest request, HttpServletResponse response){
        String token = TokenUtils.RetrieveToken(request);
        return keycloakClient.findAllUser(token, briefRepresentation, first, max);
    }

    public void updateUser(String userId, UserRepresentarioKeyCloak user, HttpServletRequest request, HttpServletResponse response){
        String token = TokenUtils.RetrieveToken(request);
        keycloakClient.updateUser(token, userId, user);
    }

    public void resetSenha(String userId, UserResetSenha rest, HttpServletRequest request, HttpServletResponse response ){
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

    public List<UserSessionRepresentation> findSessionsByUsers(String userId, HttpServletRequest request, HttpServletResponse response ){
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
        return keycloakClient.sessions(token, userId);
    }

    public void deleteSession(String idSession, HttpServletRequest request, HttpServletResponse response){
        String token = TokenUtils.RetrieveToken(request);

        UserIntrospectResponse userIntrospectResponse = userIntrospectResponse(token.substring(7, token.length()));

        if(!userIntrospectResponse.getRealm_access().getRoles().contains("ADMINISTRADORES")){
            TokenRequest tokenRequest = new TokenRequest();
            tokenRequest.setClient_id(clientId);
            tokenRequest.setClient_secret(clientSecret);
            tokenRequest.setGrant_type(grantType);
            tokenRequest.setUsername(userAdminKeycloak);
            tokenRequest.setPassword(userAdminKeycloakPassword);

            TokenResponse tokenResponse = tokenResponse(tokenRequest);
            token = "Bearer " + tokenResponse.getAccess_token();
        }
        keycloakClient.deleteSession(token, idSession);
    }
}
