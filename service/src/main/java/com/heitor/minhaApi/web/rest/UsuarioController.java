package com.heitor.minhaApi.web.rest;

import com.heitor.minhaApi.entity.Usuario;
import com.heitor.minhaApi.security.feignClient.TokenAdminResponse;
import com.heitor.minhaApi.security.feignClient.UserRepresentarioKeyCloak;
import com.heitor.minhaApi.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;

    @PostMapping("/create/step1")
    @Operation(tags = {"Usuario"}, summary = "Criar Usuario Step 1",
            description = "Requisicao para Criar Usuario Step 1", security = {@SecurityRequirement(name = "Bearer")}
    )
    public ResponseEntity<?> usuarioCreateStep1(@RequestBody @Valid Usuario usuario){
        log.info("REQUISICAO POST PARA CRIAR USUÁRIO STEP 1");
        service.createStep1(usuario);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/create/step2/{timeStamp}/{codigoConfirmacao}/{confirmadoVia}")
    @Operation(tags = {"Usuario"}, summary = "Criar Usuario Step 2",
            description = "Requisicao para Criar Usuario Step 2 - Confirmando timeStamp e codigo de confirmacao", security = {@SecurityRequirement(name = "Bearer")}
    )
    public ResponseEntity<UserRepresentarioKeyCloak> usuarioCreateStep2(
            @PathVariable("timeStamp") String timeStamp,
            @PathVariable("codigoConfirmacao") String codigoConfirmacao,
            @PathVariable(value = "confirmadoVia", required = true) String confirmadoVia){
        log.info("REQUISICAO GET PARA CRIAR USUÁRIO STEP 2");
        return ResponseEntity.ok(service.createStep2(timeStamp, codigoConfirmacao, confirmadoVia));
    }

    @PostMapping("/create/step3")
    @Operation(tags = {"Usuario"}, summary = "Criar Usuario Step 3",
            description = "Requisicao para Criar Usuario Step 3 - Finalizando Cadastro", security = {@SecurityRequirement(name = "Bearer")}
    )
    public ResponseEntity<UserRepresentarioKeyCloak> usuarioCreateStep3(@RequestBody UserRepresentarioKeyCloak user, HttpServletRequest request, HttpServletResponse response){
        log.info("REQUISICAO GET PARA CRIAR USUÁRIO STEP 3");
        user = service.createStep3(user, request, response);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(user);
    }
}
