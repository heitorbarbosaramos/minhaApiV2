package com.heitor.minhaApi.security;

import com.heitor.minhaApi.security.feignClient.*;
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
    public ResponseEntity<Void> createGroup(@RequestBody String name, HttpServletRequest request, HttpServletResponse response){
        log.info("REQUISICAO POST PARA CRIAR GROUP");
        service.createGroup(name, request, response );
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/roles/{idGroup}")
    public ResponseEntity<GroupRolesKeycloakRepresentation> groupRoles(@PathVariable("idGroup") String idGroup, HttpServletRequest request, HttpServletResponse response){
        log.info("REQUISICAO GET PARA RECUPERAR AS ROLES DE UM GRUPO");
        return ResponseEntity.ok(service.groupRoles(idGroup, request, response));
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<GroupKeycloakRepresentation>> findAll(HttpServletRequest request, HttpServletResponse response){
        log.info("REQUISICAO GET PARA RECUPERAR TODOS OS GROUPS KEYCLOAK");
        return ResponseEntity.ok(service.findAll(request, response));
    }

    @GetMapping("/findAllMembersGroup/{idGroup}")
    public ResponseEntity<List<UserRepresentarioKeyCloak>> findAllMemberByGroup(
            @RequestParam(value = "first", defaultValue = "0") Integer first,
            @RequestParam(value = "max", defaultValue = "10") Integer max,
            @PathVariable("idGroup") String idGroup,
            HttpServletRequest request, HttpServletResponse response){
        log.info("REQUISICAO GET PARA RECUPERAR TODOS OS GROUPS KEYCLOAK");
        return ResponseEntity.ok(service.findAllMembersByGroup(idGroup, first, max, request, response));
    }

    @GetMapping("/editGroupFindRoles/{idGroup}")
    public ResponseEntity<List<RolesRepresentationKeycloak>> editGroupFindRoles(
            @PathVariable("idGroup") String idGroup,
            @RequestParam(value = "first", defaultValue = "0") Integer first,
            @RequestParam(value = "max", defaultValue = "10") Integer max,
            HttpServletRequest request, HttpServletResponse response){
        log.info("REQUISICAO GET PARA RECUPERAR LISTA DE ROLES PARA ADICIONAR A UM GRUPO DE USUARIOS");
        return ResponseEntity.ok(service.editGroupFindRoles(idGroup, first, max, request, response));
    }

    @PostMapping("/editGroupAddRoles/{idGroup}")
    public ResponseEntity<GroupRolesKeycloakRepresentation> editGroupAddRoles(@RequestBody List<RolesRepresentationKeycloak> roles,
                                                  @PathVariable("idGroup") String idGroup,
                                                  HttpServletRequest request, HttpServletResponse response){
        log.info("REQUISICAO POST PARA ADCIONAR ROLES A UM GRUPO");
        return ResponseEntity.ok(service.editGroupAddRoles(roles, idGroup, request, response));
    }

    @DeleteMapping("/editGroupRemoveRoles/{idGroup}")
    public ResponseEntity<GroupRolesKeycloakRepresentation> editGroupRemoveRoles(@RequestBody List<DeleteGroupRole> roles,
                                                                              @PathVariable("idGroup") String idGroup,
                                                                              HttpServletRequest request, HttpServletResponse response){
        log.info("REQUISICAO POST PARA ADCIONAR ROLES A UM GRUPO");
        return ResponseEntity.ok(service.editGroupRemoveRoles(roles, idGroup, request, response));
    }

    @PutMapping("/editGroupAddUsuario/{idGroup}/{idUser}")
    public ResponseEntity<List<UserRepresentarioKeyCloak>> editGroupAddUser(
            @PathVariable("idUser") String idUser,
            @PathVariable("idGroup") String idGroup,
            HttpServletRequest request, HttpServletResponse response){
        log.info("REQUISICAO PUT PARA ADCIONAR USUARIO AO GRUPO");
        return ResponseEntity.ok(service.editGroupAddUser(idUser, idGroup, request, response));
    }

    @DeleteMapping("/editGroupRemoveUsuario/{idGroup}/{idUser}")
    public ResponseEntity<List<UserRepresentarioKeyCloak>> editGroupRemoveUser(
            @PathVariable("idUser") String idUser,
            @PathVariable("idGroup") String idGroup,
            HttpServletRequest request, HttpServletResponse response){
        log.info("REQUISICAO DELETE PARA REMOVER USUARIO DO GRUPO");
        return ResponseEntity.ok(service.editGroupRemoveUser(idUser, idGroup, request, response));
    }
}
