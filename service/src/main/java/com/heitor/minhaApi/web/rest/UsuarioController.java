package com.heitor.minhaApi.web.rest;

import com.heitor.minhaApi.security.dto.UsuarioCreateDTO;
import com.heitor.minhaApi.security.feignClient.UserRepresentarioKeyCloak;
import com.heitor.minhaApi.security.feignClient.UserResetSenha;
import com.heitor.minhaApi.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;

    @PostMapping("/create/step1")
    @Operation(tags = {"Usuario"}, summary = "Criar Usuario Step 1",
            description = "Requisicao para Criar Usuario Step 1"
    )
    public ResponseEntity<?> usuarioCreateStep1(@RequestBody @Valid UsuarioCreateDTO usuario){
        log.info("REQUISICAO POST PARA CRIAR USUÁRIO STEP 1");
        service.createStep1(usuario);
        String message = "";
        switch (usuario.getMetodoAtivacao()){
            case EMAIL :
                message = "Foi enviado um email para "+usuario.getEmail()+" para continuar o processo de cadastro";
                break;
            case WHATSAPP:
                message = "Foi enviado uma mensagem para "+usuario.getTelefone()+" para continuar o processo de cadastro";
        }
        return ResponseEntity.ok(message);
    }

    @GetMapping("/create/step2/{timeStamp}/{codigoConfirmacao}/{confirmadoVia}")
    @Operation(tags = {"Usuario"}, summary = "Criar Usuario Step 2",
            description = "Requisicao para Criar Usuario Step 2 - Confirmando timeStamp e codigo de confirmacao", security = {}
    )
    public ResponseEntity<Void> usuarioCreateStep2(
            @PathVariable("timeStamp") String timeStamp,
            @PathVariable("codigoConfirmacao") String codigoConfirmacao,
            @PathVariable(value = "confirmadoVia", required = true) String confirmadoVia,
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("REQUISICAO GET PARA CRIAR USUÁRIO STEP 2");
        service.createStep2(timeStamp, codigoConfirmacao, confirmadoVia, request, response);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/create/step3/{timeStamp}/{codigoConfirmacao}/{ativado}")
    @Operation(tags = {"Usuario"}, summary = "Criar Usuario Step 3",
            description = "Requisicao para Criar Usuario Step 3 - Recuperando dados para o formulario", security = {}
    )
    public ResponseEntity<UserRepresentarioKeyCloak> usuarioCreateStep3(
            @PathVariable("timeStamp") Long timeStamp,
            @PathVariable("codigoConfirmacao") String codigoConfirmacao,
            @PathVariable("ativado") String ativado
    ){
        log.info("REQUISICAO GET PARA CRIAR USUÁRIO STEP 3");
        return ResponseEntity.ok(service.creatStep3(timeStamp, codigoConfirmacao, ativado));
    }

    @PostMapping("/create/step4")
    @Operation(tags = {"Usuario"}, summary = "Criar Usuario Step 4",
            description = "Requisicao para Criar Usuario Step 4 - Finalizando Cadastro", security = {}
    )
    public void usuarioCreateStep4(@RequestBody UserRepresentarioKeyCloak user, HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("REQUISICAO GET PARA CRIAR USUÁRIO STEP 4");
        service.createStep4(user, request, response);
    }

    @PutMapping("/create/step5/{timeStamp}/{codigoConfirmacao}/{ativado}")
    @Operation(tags = {"Usuario"}, summary = "Criar Usuario Step 5",
            description = "Requisicao para Criar Usuario Step 5 - Cadastrar senha", security = {}
    )
    public void usuarioCreatStep5(
            @RequestBody UserResetSenha rest,
            @PathVariable("timeStamp") String timeStamp,
            @PathVariable("codigoConfirmacao") String codigoConfirmacao,
            @PathVariable("ativado") String ativado,
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("REQUISICAO PUT PARA CRIAR USUARIO STEP 5");
        service.createStep5(timeStamp, rest, codigoConfirmacao, ativado, request, response);
    }


}
