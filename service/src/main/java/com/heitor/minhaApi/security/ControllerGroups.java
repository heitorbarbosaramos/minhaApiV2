package com.heitor.minhaApi.security;

import com.heitor.minhaApi.security.feignClient.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
public class ControllerGroups {

    private final GroupsKeycloakService service;

    @PostMapping("/create")
    @Operation(tags = {"Grupo de Usuários"}, summary = "Realizar a criação de uma grupo",
            description = "Requisição POST para Realizar a criação de uma grupo", security = {@SecurityRequirement(name = "Bearer")}
    )
    public ResponseEntity<Void> createGroup(@RequestBody String name, HttpServletRequest request, HttpServletResponse response){
        log.info("REQUISICAO POST PARA CRIAR GROUP");
        service.createGroup(name, request, response );
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/roles/{idGroup}")
    @Operation(tags = {"Grupo de Usuários"}, summary = "Realizar a recuperação das Roles de um grupo",
            description = "Requisição GET para Realizar a recuperação das Roles de um grupo", security = {@SecurityRequirement(name = "Bearer")}
    )
    public ResponseEntity<GroupRolesKeycloakRepresentation> groupRoles(@PathVariable("idGroup") String idGroup, HttpServletRequest request, HttpServletResponse response){
        log.info("REQUISICAO GET PARA RECUPERAR AS ROLES DE UM GRUPO");
        return ResponseEntity.ok(service.groupRoles(idGroup, request, response));
    }

    @GetMapping("/findAll")
    @Operation(tags = {"Grupo de Usuários"}, summary = "Realizar a recuperação de todos os Grupos",
            description = "Requisição GET para Realizar a recuperação de todos os Grupos", security = {@SecurityRequirement(name = "Bearer")}
    )
    public ResponseEntity<List<GroupKeycloakRepresentation>> findAll(HttpServletRequest request, HttpServletResponse response){
        log.info("REQUISICAO GET PARA RECUPERAR TODOS OS GROUPS KEYCLOAK");
        return ResponseEntity.ok(service.findAll(request, response));
    }

    @GetMapping("/findAllMembersGroup/{idGroup}")
    @Operation(tags = {"Grupo de Usuários"}, summary = "Realizar a recuperação de todos membros de um Grupos",
            description = "Requisição GET para Realizar a recuperação de todos membros de um Grupos", security = {@SecurityRequirement(name = "Bearer")}
    )
    public ResponseEntity<List<UserRepresentarioKeyCloak>> findAllMemberByGroup(
            @RequestParam(value = "first", defaultValue = "0") Integer first,
            @RequestParam(value = "max", defaultValue = "10") Integer max,
            @PathVariable("idGroup") String idGroup,
            HttpServletRequest request, HttpServletResponse response){
        log.info("REQUISICAO GET PARA RECUPERAR TODOS OS GROUPS KEYCLOAK");
        return ResponseEntity.ok(service.findAllMembersByGroup(idGroup, first, max, request, response));
    }

    @GetMapping("/editGroupFindRoles/{idGroup}")
    @Operation(tags = {"Grupo de Usuários"}, summary = "Realizar recuperação das Roles para adicionar a um grupo",
            description = "Requisição GET para Realizar recuperação das Roles para adicionar a um grupo", security = {@SecurityRequirement(name = "Bearer")}
    )
    public ResponseEntity<List<RolesRepresentationKeycloak>> editGroupFindRoles(
            @PathVariable("idGroup") String idGroup,
            @RequestParam(value = "first", defaultValue = "0") Integer first,
            @RequestParam(value = "max", defaultValue = "10") Integer max,
            HttpServletRequest request, HttpServletResponse response){
        log.info("REQUISICAO GET PARA RECUPERAR LISTA DE ROLES PARA ADICIONAR A UM GRUPO DE USUARIOS");
        return ResponseEntity.ok(service.editGroupFindRoles(idGroup, first, max, request, response));
    }

    @PostMapping("/editGroupAddRoles/{idGroup}")
    @Operation(tags = {"Grupo de Usuários"}, summary = "Realizar recuperação das Roles para adicionar a um grupo",
            description = "Requisição GET para Realizar recuperação das Roles para adicionar a um grupo", security = {@SecurityRequirement(name = "Bearer")}
    )
    public ResponseEntity<GroupRolesKeycloakRepresentation> editGroupAddRoles(@RequestBody List<RolesRepresentationKeycloak> roles,
                                                  @PathVariable("idGroup") String idGroup,
                                                  HttpServletRequest request, HttpServletResponse response){
        log.info("REQUISICAO POST PARA ADCIONAR ROLES A UM GRUPO");
        return ResponseEntity.ok(service.editGroupAddRoles(roles, idGroup, request, response));
    }

    @DeleteMapping("/editGroupRemoveRoles/{idGroup}")
    @Operation(tags = {"Grupo de Usuários"}, summary = "Realizar remoção das Roles de um grupo",
            description = "Requisição GET para Realizar remoção das Roles de um grupo", security = {@SecurityRequirement(name = "Bearer")}
    )
    public ResponseEntity<GroupRolesKeycloakRepresentation> editGroupRemoveRoles(@RequestBody List<DeleteGroupRole> roles,
                                                                              @PathVariable("idGroup") String idGroup,
                                                                              HttpServletRequest request, HttpServletResponse response){
        log.info("REQUISICAO POST PARA ADCIONAR ROLES A UM GRUPO");
        return ResponseEntity.ok(service.editGroupRemoveRoles(roles, idGroup, request, response));
    }

    @PutMapping("/editGroupAddUsuario/{idGroup}/{idUser}")
    @Operation(tags = {"Grupo de Usuários"}, summary = "Realizar adição de Usuario a um grupo",
            description = "Requisição GET para Realizar adição de Usuario a um grupo", security = {@SecurityRequirement(name = "Bearer")}
    )
    public ResponseEntity<List<UserRepresentarioKeyCloak>> editGroupAddUser(
            @PathVariable("idUser") String idUser,
            @PathVariable("idGroup") String idGroup,
            HttpServletRequest request, HttpServletResponse response){
        log.info("REQUISICAO PUT PARA ADCIONAR USUARIO AO GRUPO");
        return ResponseEntity.ok(service.editGroupAddUser(idUser, idGroup, request, response));
    }

    @DeleteMapping("/editGroupRemoveUsuario/{idGroup}/{idUser}")
    @Operation(tags = {"Grupo de Usuários"}, summary = "Realizar remoção de Usuario a um grupo",
            description = "Requisição GET para Realizar remoção de Usuario a um grupo", security = {@SecurityRequirement(name = "Bearer")}
    )
    public ResponseEntity<List<UserRepresentarioKeyCloak>> editGroupRemoveUser(
            @PathVariable("idUser") String idUser,
            @PathVariable("idGroup") String idGroup,
            HttpServletRequest request, HttpServletResponse response){
        log.info("REQUISICAO DELETE PARA REMOVER USUARIO DO GRUPO");
        return ResponseEntity.ok(service.editGroupRemoveUser(idUser, idGroup, request, response));
    }
}
