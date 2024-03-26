package com.heitor.minhaApi.web.rest;

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
    public ResponseEntity<?> admin(){
        log.info("REQUISICAO GET PARA ADMINISTRADORES");
        return ResponseEntity.ok().body("Você é um administrador");
    }

    @GetMapping("/operation")
    @PreAuthorize("hasAnyAuthority('OPERATION')")
    public ResponseEntity<?> operation(){
        log.info("REQUISICAO GET PARA OPERATIONS");
        return ResponseEntity.ok().body("Você é um operador");
    }

    @GetMapping("/mixed")
    @PreAuthorize("hasAnyAuthority('OPERATION', 'ADMINISTRADORES')")
    public ResponseEntity<?> mixed(){
        log.info("REQUISICAO GET PARA OPERATIONS E ADMINISTRADORES");
        return ResponseEntity.ok().body("Você é um operador ou  administrador");
    }

    @GetMapping("/free")
    public ResponseEntity<?> free(){
        log.info("REQUISICAO GET PARA QUALQUER USUARIO LOGADO");
        return ResponseEntity.ok().body("Você é um usuario logado");
    }
}
