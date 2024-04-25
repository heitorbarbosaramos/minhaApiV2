package com.heitor.minhaApi.security.feignClient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RolesRepresentationKeycloak {

    private String id;
    private String name;
    private String description;
    private String composite;
    private String clientRole;
    private String containerId;
}
