package com.heitor.minhaApi.security;

import com.heitor.minhaApi.security.feignClient.UserRepresentarioKeyCloak;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class ControllerAuth {

    private final LoginService service;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UsuarioLoginDTO loginDTO, HttpServletRequest request, HttpServletResponse response){
        log.info("REQUISICAO POST PARA REALIZAR LOGIN");
        return ResponseEntity.ok(service.login(request, response, loginDTO));
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(@RequestParam("refresh_token") String refreshToken){
        log.info("REQUISICAO POST PARA REALIZAR REFRESH TOKEN");
        return ResponseEntity.ok(service.refreshToken(refreshToken));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody UserRepresentarioKeyCloak user, HttpServletRequest request, HttpServletResponse response){
        log.info("REQUISICAO POST PARA CRIAR USUARIO LOGIN");
        service.createUser(user, request, response);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable("userId") String userId,@RequestBody UserRepresentarioKeyCloak user, HttpServletRequest request, HttpServletResponse response){
        log.info("REQUISICAO PUT PARA ATUALIZAR USUARIO");
        service.updateUser(userId, user, request, response);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
