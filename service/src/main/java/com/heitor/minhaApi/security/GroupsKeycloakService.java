package com.heitor.minhaApi.security;

import com.heitor.minhaApi.security.feignClient.GroupKeycloakRepresentation;
import com.heitor.minhaApi.security.feignClient.KeycloakClient;
import com.heitor.minhaApi.security.utils.TokenUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupsKeycloakService {

    private final KeycloakClient keycloakClient;

    public List<GroupKeycloakRepresentation> findAll(HttpServletRequest request, HttpServletResponse response){
        String token = TokenUtils.RetrieveToken(request);
        return keycloakClient.groupsKeycloak(token);
    }


}
