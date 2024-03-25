package com.heitor.minhaApi.security;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ControllerAuth {

    private final LoginService service;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UsuarioLoginDTO loginDTO){
        log.info("REQUISICAO POST PARA REALIZAR LOGIN");
        return ResponseEntity.ok(service.login(loginDTO));
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(@RequestParam("refresh_token") String refreshToken){
        log.info("REQUISICAO POST PARA REALIZAR REFRESH TOKEN");
        return ResponseEntity.ok(service.refreshToken(refreshToken));
    }

}
