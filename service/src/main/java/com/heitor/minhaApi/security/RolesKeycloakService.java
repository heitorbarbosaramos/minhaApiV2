package com.heitor.minhaApi.security;

import com.heitor.minhaApi.security.feignClient.KeycloakClient;
import com.heitor.minhaApi.security.feignClient.RolesRepresentationKeycloak;
import com.heitor.minhaApi.security.utils.TokenUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RolesKeycloakService {

    private final KeycloakClient keycloakClient;

    public List<RolesRepresentationKeycloak> findAll(HttpServletRequest request, HttpServletResponse response){
        String token = TokenUtils.RetrieveToken(request);
        return keycloakClient.rolesKeycloak(token);
    }

    public RolesRepresentationKeycloak findByName(String roleName, HttpServletRequest request, HttpServletResponse response){
        String token = TokenUtils.RetrieveToken(request);
        return keycloakClient.findByName(token, roleName);
    }

    public RolesRepresentationKeycloak updateRole(String roleName, RolesRepresentationKeycloak role, HttpServletRequest request, HttpServletResponse response){
        String token = TokenUtils.RetrieveToken(request);
        keycloakClient.updateRoleKeycloak(token, roleName, role);
        return findByName(roleName, request, response);
    }

    public RolesRepresentationKeycloak createRole(RolesRepresentationKeycloak role, HttpServletRequest request, HttpServletResponse response){
        String token = TokenUtils.RetrieveToken(request);
        keycloakClient.createRoleKeycloak(token, role);
        return findByName(role.getName(), request, response);
    }
}
