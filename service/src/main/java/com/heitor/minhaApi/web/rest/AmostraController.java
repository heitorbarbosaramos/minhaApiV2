package com.heitor.minhaApi.web.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/amostra")
public class AmostraController {

    @GetMapping("/admin")
    @PreAuthorize("hasAnyAuthority('ADMINISTRADORES')")
    @Operation(tags = {"Amostra"}, summary = "Amostra somente ADMINISTRADORES PODEM ACESSAR",
            description = "Amostra somente ADMINISTRADORES PODEM ACESSAR", security = {@SecurityRequirement(name = "Bearer")}
    )
    public ResponseEntity<?> admin(){
        log.info("REQUISICAO GET PARA ADMINISTRADORES");
        return ResponseEntity.ok().body("Você é um administrador");
    }

    @GetMapping("/operation")
    @Operation(tags = {"Amostra"}, summary = "Amostra somente OPERADORES PODEM ACESSAR",
            description = "Amostra somente OPERADORES PODEM ACESSAR", security = {@SecurityRequirement(name = "Bearer")}
    )
    @PreAuthorize("hasAnyAuthority('OPERATION')")

    public ResponseEntity<?> operation(){
        log.info("REQUISICAO GET PARA OPERATIONS");
        return ResponseEntity.ok().body("Você é um operador");
    }

    @GetMapping("/mixed")
    @PreAuthorize("hasAnyAuthority('OPERATION', 'ADMINISTRADORES')")
    @Operation(tags = {"Amostra"}, summary = "Amostra somente ADMINISTRADORES E OPERADORES PODEM ACESSAR",
            description = "Amostra somente ADMINISTRADORES E OPERADORES PODEM ACESSAR", security = {@SecurityRequirement(name = "Bearer")}
    )
    public ResponseEntity<?> mixed(){
        log.info("REQUISICAO GET PARA OPERATIONS E ADMINISTRADORES");
        return ResponseEntity.ok().body("Você é um operador ou  administrador");
    }

    @GetMapping("/free")
    @Operation(tags = {"Amostra"}, summary = "Amostra acesso livre somente para quem estiver autenticado",
            description = "Amostra acesso livre somente para quem estiver autenticado", security = {@SecurityRequirement(name = "Bearer")}
    )
    public ResponseEntity<?> free(){
        log.info("REQUISICAO GET PARA QUALQUER USUARIO LOGADO");
        return ResponseEntity.ok().body("Você é um usuario logado");
    }
}
