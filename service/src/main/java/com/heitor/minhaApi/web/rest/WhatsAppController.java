package com.heitor.minhaApi.web.rest;

import com.heitor.minhaApi.feign.SendMessageRequest;
import com.heitor.minhaApi.feign.SendMessageResponse;
import com.heitor.minhaApi.feign.WhatsAppSessionResponse;
import com.heitor.minhaApi.service.whatsapp.WhatsAppService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/whatsApp")
@RequiredArgsConstructor
public class WhatsAppController {

    private final WhatsAppService service;

    @GetMapping("/create/session/{session}")
    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR')")
    @Operation(tags = {"WhatsApp"}, summary = "Criar Session",
            description = "Requisicao GET para Criar Session WhatsApp",security = {@SecurityRequirement(name = "Bearer")}
    )
    public ResponseEntity<WhatsAppSessionResponse> createSession(@PathVariable("session") String session){
        log.info("REQUISICAO GET PARA CRIAR SESSION WHATSAPP");
        return ResponseEntity.ok(service.criarSession(session));
    }

    @GetMapping("/status/session/{session}")
    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR')")
    @Operation(tags = {"WhatsApp"}, summary = "Status Session",
            description = "Requisicao GET para Status Session WhatsApp",security = {@SecurityRequirement(name = "Bearer")}
    )
    public ResponseEntity<WhatsAppSessionResponse> recoveryStatusRecovery(@PathVariable("session") String session){
        log.info("REQUISICAO GET PARA RECUPERAR STATUS SESSION WHATSAPP");
        return ResponseEntity.ok(service.recoveryStatusSession(session));
    }

    @GetMapping("/codeQr/session/{session}")
    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR')")
    @Operation(tags = {"WhatsApp"}, summary = "Recuperar o código do QR Code",
            description = "Requisicao GET para Recuperar o código do QR Code",security = {@SecurityRequirement(name = "Bearer")}
    )
    public ResponseEntity<WhatsAppSessionResponse> recoveryQrRecovery(@PathVariable("session") String session){
        log.info("REQUISICAO GET PARA RECUPERAR CODE QRCODE SESSION WHATSAPP");
        return ResponseEntity.ok(service.recoveryCodeQr(session));
    }

    @GetMapping("/codeQrImagem/session/{session}")
    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR')")
    @Operation(tags = {"WhatsApp"}, summary = "Recuperar QR code da Session",
            description = "Requisicao GET para Recuperar QR code da Session",security = {@SecurityRequirement(name = "Bearer")}
    )
    public ResponseEntity<byte[]> recoveryImagemQRCode(@PathVariable("session") String session) throws IOException {
        log.info("REQUISICAO GET PARA RECUPERAR IMAGEM QRCODE SESSION");
        byte[] image = service.recoveryImagemPngQRCode(session);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentLength(image.length);

        return ResponseEntity.ok().headers(headers).body(image);
    }

    @PostMapping("/sendMessage/{session}")
    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR')")
    @Operation(tags = {"WhatsApp"}, summary = "Envia Mensagem",
            description = "Requisicao POST para Envia Mensagem",security = {@SecurityRequirement(name = "Bearer")}
    )
    public ResponseEntity<SendMessageResponse> sendMessage(@PathVariable("session") String session, @RequestBody @Valid SendMessageRequest request){
        log.info("REQUISICAO POST PARA ENVIAR MENSAGEM VIA WHATSAPP");
        return ResponseEntity.ok(service.sendMessage(session, request));
    }
}
