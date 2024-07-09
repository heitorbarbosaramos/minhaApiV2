package com.heitor.minhaApi.security;

import com.heitor.minhaApi.security.feignClient.UserRepresentarioKeyCloak;
import com.heitor.minhaApi.security.feignClient.UserResetSenha;
import com.heitor.minhaApi.security.feignClient.UserSessionRepresentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class ControllerAuth {

    private final LoginService service;

    @PostMapping("/login")
    @Operation(tags = {"Autenticacao"}, summary = "Realizar login através de um usuário e senha",
            description = "Requisição POST para Realizar login através de um usuário e senha"
    )
    public ResponseEntity<?> login(@Valid @RequestBody UsuarioLoginDTO loginDTO, HttpServletRequest request, HttpServletResponse response){
        log.info("REQUISICAO POST PARA REALIZAR LOGIN");
        return ResponseEntity.ok(service.login(request, response, loginDTO));
    }

    @GetMapping("/logout")
    @Operation(tags = {"Autenticacao"}, summary = "Realizar logout",
            description = "Requisição GET para Realizar logout", security = {@SecurityRequirement(name = "Bearer")}
    )
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("REQUISICAO GET PARA FAZER LOGOUT");
        service.logout(request, response);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/refreshToken")
    @Operation(tags = {"Autenticacao"}, summary = "Realizar o refresh Token",
            description = "Requisição POST para Realizar o refresh Token", security = {@SecurityRequirement(name = "Bearer")}
    )
    public ResponseEntity<?> refreshToken(@RequestParam("refresh_token") String refreshToken){
        log.info("REQUISICAO POST PARA REALIZAR REFRESH TOKEN");
        return ResponseEntity.ok(service.refreshToken(refreshToken));
    }

    @PostMapping("/create")
    @Operation(tags = {"Autenticacao"}, summary = "Realizar a criação de um usuário",
            description = "Requisição POST para Realizar a criação de um usuário", security = {@SecurityRequirement(name = "Bearer")}
    )
    public ResponseEntity<UserRepresentarioKeyCloak> createUser(@RequestBody UserRepresentarioKeyCloak user, HttpServletRequest request, HttpServletResponse response){
        log.info("REQUISICAO POST PARA CRIAR USUARIO LOGIN");
        UserRepresentarioKeyCloak retorno = service.createUser(user, request, response);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(retorno.getId()).toUri();
        return ResponseEntity.created(uri).body(retorno);
    }

    @GetMapping("/findAll")
    @Operation(tags = {"Autenticacao"}, summary = "Realizar a recuperação de todos os usuário",
            description = "Requisição GET para Realizar a recuperação de todos os usuário", security = {@SecurityRequirement(name = "Bearer")}
    )
    public ResponseEntity<List<UserRepresentarioKeyCloak>> findAllUser(
            @RequestParam(value = "briefRepresentation", defaultValue = "true") String briefRepresentation,
            @RequestParam(value = "first", defaultValue = "0") Integer first,
            @RequestParam(value = "max", defaultValue = "10") Integer max,
            HttpServletRequest request,
            HttpServletResponse response){
        log.info("REQUIISICAO GET PARA RECUPERAR TODOS OS USUARIO KEYCLOAK");
        return ResponseEntity.ok(service.findAllUser(briefRepresentation, first, max,  request,response));
    }

    @GetMapping("/findByUserName/{userName}")
    @Operation(tags = {"Autenticacao"}, summary = "Realizar a recuperação de um usuário pelo nome de usuário",
            description = "Requisição GET para Realizar a recuperação de um usuário pelo nome de usuário", security = {@SecurityRequirement(name = "Bearer")}
    )
    public ResponseEntity<List<UserRepresentarioKeyCloak>> findByUserName(@PathVariable("userName") String userName, HttpServletRequest request, HttpServletResponse response){
        log.info("REQUIISICAO GET PARA RECUPERAR USUARIO PELO USERNAME KEYCLOAK");
        return ResponseEntity.ok(service.findByUserName(userName, request,response));
    }

    @PutMapping("/update/{userId}")
    @Operation(tags = {"Autenticacao"}, summary = "Realizar a atualização de um usuário",
            description = "Requisição PUT para Realizar a atualização de um usuário", security = {@SecurityRequirement(name = "Bearer")}
    )
    public ResponseEntity<?> updateUser(@PathVariable("userId") String userId,@RequestBody UserRepresentarioKeyCloak user, HttpServletRequest request, HttpServletResponse response){
        log.info("REQUISICAO PUT PARA ATUALIZAR USUARIO");
        service.updateUser(userId, user, request, response);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/resetPassword/{userId}")
    @Operation(tags = {"Autenticacao"}, summary = "Realizar reset senha de um usuário",
            description = "Requisição PUT para Realizar reset senha de um usuário", security = {@SecurityRequirement(name = "Bearer")}
    )
    public ResponseEntity<Void> resetPassWord(
            @PathVariable("userId") String userId,
            @RequestBody UserResetSenha rest, HttpServletRequest request, HttpServletResponse response){
        log.info("REQUISICAO PATCH PARA RESETAR SENHA");
        service.resetSenha(userId, rest, request, response);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/sessions/{userId}")
    @Operation(tags = {"Autenticacao"}, summary = "Realizar busca nas sessions do usuário",
            description = "Requisição PUT para busca nas sessions do usuário", security = {@SecurityRequirement(name = "Bearer")}
    )
    public ResponseEntity<List<UserSessionRepresentation>> findSessiosByUser(@PathVariable("userId") String userId, HttpServletRequest request, HttpServletResponse response){
        log.info("REQUISICAO GET PARA BUSCAR SESSIONS ABERTA DO USUÁRIO");
        return ResponseEntity.ok(service.findSessionsByUsers(userId, request, response));
    }

    @DeleteMapping("/deleteSession/{idSessiona}")
    public ResponseEntity<Void> deleteSession(@PathVariable("idSessiona") String idSession, HttpServletRequest request, HttpServletResponse response){
        log.info("REQUISICAO DELETE PARA DELETAR UMA SESSION DO USUARIO");
        service.deleteSession(idSession, request, response);
        return ResponseEntity.noContent().build();
    }


}
