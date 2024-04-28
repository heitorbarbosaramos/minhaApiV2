package com.heitor.minhaApi.web.rest;

import com.heitor.minhaApi.entity.Endereco;
import com.heitor.minhaApi.service.EnderecoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/endereco")
@RequiredArgsConstructor
public class EnderecoController {

    private final EnderecoService service;

    @GetMapping("/findBYCep/{cep}")
    @Operation(tags = {"Endereco"}, summary = "Recuperar endereco pelo CEP",
            description = "Recuperar endereco pelo CEP", security = {@SecurityRequirement(name = "Bearer")}
    )
    public ResponseEntity<Endereco> findByCep(@PathVariable("cep") String cep){
        log.info("REQUISICAO GET PARA RECUPERAR ENDERECO PELO CEP");
        return ResponseEntity.ok(service.findByCep(cep));
    }
}
