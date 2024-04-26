package com.heitor.minhaApi.security;

import com.heitor.minhaApi.security.feignClient.*;
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

    public List<UserRepresentarioKeyCloak> findAllMembersByGroup(String idGroup, Integer first, Integer max, HttpServletRequest request, HttpServletResponse response){
        String token = TokenUtils.RetrieveToken(request);
        return keycloakClient.membersGroup(token, idGroup, first, max);
    }

    public void createGroup(String name, HttpServletRequest request, HttpServletResponse response){
        String token = TokenUtils.RetrieveToken(request);
        keycloakClient.createGroup(token, name);
    }

    public GroupRolesKeycloakRepresentation groupRoles(String idGroup, HttpServletRequest request, HttpServletResponse response){
        String token = TokenUtils.RetrieveToken(request);
        return keycloakClient.groupsRoles(token, idGroup);
    }

    public List<RolesRepresentationKeycloak> editGroupFindRoles(
            String idGroup, Integer first, Integer max,
            HttpServletRequest request, HttpServletResponse response){
        String token = TokenUtils.RetrieveToken(request);
        return keycloakClient.editGroupFindRoles(token, idGroup, first, max);
    }

    public GroupRolesKeycloakRepresentation editGroupAddRoles(List<RolesRepresentationKeycloak> roles, String idGroup, HttpServletRequest request, HttpServletResponse response){
        String token = TokenUtils.RetrieveToken(request);
        keycloakClient.editGroupAddRoles(token,idGroup, roles);
        return groupRoles(idGroup, request, response);
    }

    public GroupRolesKeycloakRepresentation editGroupRemoveRoles(List<DeleteGroupRole> roles, String idGroup, HttpServletRequest request, HttpServletResponse response){
        String token = TokenUtils.RetrieveToken(request);
        keycloakClient.editGroupRemoveRoles(token,idGroup, roles);
        return groupRoles(idGroup, request, response);
    }

    public List<UserRepresentarioKeyCloak> editGroupAddUser(String idUser, String idGroup, HttpServletRequest request, HttpServletResponse response){
        String token = TokenUtils.RetrieveToken(request);
        keycloakClient.editGroupAddUser(token, idUser, idGroup);
        return findAllMembersByGroup(idGroup, 0, 10, request, response);
    }

    public List<UserRepresentarioKeyCloak> editGroupRemoveUser(String idUser, String idGroup, HttpServletRequest request, HttpServletResponse response){
        String token = TokenUtils.RetrieveToken(request);
        keycloakClient.editGroupRemoveUser(token, idUser, idGroup);
        return findAllMembersByGroup(idGroup, 0, 10, request, response);
    }



}
