package com.heitor.minhaApi.security;

import com.heitor.minhaApi.security.feignClient.GroupKeycloakRepresentation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
public class ControllerGroups {

    private final GroupsKeycloakService service;

    @GetMapping("/findAll")
    public ResponseEntity<List<GroupKeycloakRepresentation>> findAll(HttpServletRequest request, HttpServletResponse response){
        log.info("REQUISICAO GET PARA RECUPERAR TODOS OS GROUPS KEYCLOAK");
        return ResponseEntity.ok(service.findAll(request, response));
    }
}
