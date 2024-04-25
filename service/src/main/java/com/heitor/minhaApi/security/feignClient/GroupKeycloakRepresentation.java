package com.heitor.minhaApi.security.feignClient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupKeycloakRepresentation {

    private String id;
    private String name;
    private String path;
    private List<GroupKeycloakRepresentation> subGroups;
}
