package com.heitor.minhaApi.security.feignClient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupRolesKeycloakRepresentation {

    private String id;
    private String name;
    private String path;
    //private List<String> attributes;
    private List<String> realmRoles;
    private GroupClientRolesKeyCloakRepresentation clientRoles;
    private HashMap<String, Boolean> access;

}
