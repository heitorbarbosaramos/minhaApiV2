package com.heitor.minhaApi.security;

import com.heitor.minhaApi.security.feignClient.RolesRepresentationKeycloak;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class ControllerRoles {

    private final RolesKeycloakService service;

    @GetMapping("/findAll")
    @Operation(tags = {"Roles"}, summary = "Realizar recuperação de todas as Roles",
            description = "Realizar recuperação de todas as Roles", security = {@SecurityRequirement(name = "Bearer")}
    )
    public ResponseEntity<List<RolesRepresentationKeycloak>> findAll(HttpServletRequest request, HttpServletResponse response){
        log.info("REQUISICAO GET PARA RECUPERAR TODAS AS ROLES DO KEYCLOAK");
        return ResponseEntity.ok(service.findAll(request, response));
    }

    @GetMapping("/findByName/{roleName}")
    @Operation(tags = {"Roles"}, summary = "Realizar recuperação de Roles pelo nome",
            description = "Realizar recuperação de Roles pelo nome", security = {@SecurityRequirement(name = "Bearer")}
    )
    public ResponseEntity<RolesRepresentationKeycloak> findByName(@PathVariable("roleName") String roleName, HttpServletRequest request, HttpServletResponse response){
        log.info("REQUISICAO GET PARA RECUPERAR ROLE KEYCLOAK PELO NOME");
        return ResponseEntity.ok(service.findByName(roleName, request, response));
    }

    @PostMapping("/create")
    @Operation(tags = {"Roles"}, summary = "Realizar criação de Roles",
            description = "Realizar criação de Roles", security = {@SecurityRequirement(name = "Bearer")}
    )
    public ResponseEntity<RolesRepresentationKeycloak> create(@RequestBody RolesRepresentationKeycloak role, HttpServletRequest request, HttpServletResponse response){
        log.info("REQUISICAO POST PARA CRIAR ROLE KEYCLOAK");
        return ResponseEntity.ok(service.createRole(role, request, response));
    }

    @PutMapping("/update/{roleName}")
    @Operation(tags = {"Roles"}, summary = "Realizar update de Roles",
            description = "Realizar update de Roles", security = {@SecurityRequirement(name = "Bearer")}
    )
    public ResponseEntity<RolesRepresentationKeycloak> update(@PathVariable("roleName") String roleName, @RequestBody RolesRepresentationKeycloak role, HttpServletRequest request, HttpServletResponse response){
        log.info("REQUISICAO PUT PARA ATUALIZAR ROLE KEYCLOAK");
        return ResponseEntity.ok(service.updateRole(roleName, role, request, response));
    }
}
