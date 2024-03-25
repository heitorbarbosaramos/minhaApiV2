package com.heitor.minhaApi.exceptions;

import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ResourcesExceptionsHandler {

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<MensagemPadrao> uncategorizedSQLException(FeignException e, HttpServletRequest request){

        HttpStatus httpStatus;
        MensagemPadrao obj = new MensagemPadrao();

        if(e.getMessage().contains("401")){
            httpStatus = HttpStatus.UNAUTHORIZED;
            obj.setMensagem("Usuário ou senha estão errados ou usuário não exite");
        }else if(e.getMessage().contains("403")) {
            httpStatus = HttpStatus.FORBIDDEN;
            obj.setMensagem("Usuário não tempermissão para acessar o recurso solicitado");
        } else if (e.getMessage().contains("400") && e.getMessage().contains("Account disabled")) {
            httpStatus = HttpStatus.FORBIDDEN;
            obj.setMensagem("Usuário desativado");
        } else if (e.getMessage().contains("400") && e.getMessage().contains("Invalid refresh token")) {
                httpStatus = HttpStatus.FORBIDDEN;
                obj.setMensagem("Token de acesso inválido");
        }else{
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            obj.setMensagem(e.getMessage());
        }


        obj.setIdStatus(httpStatus.value());
        obj.setCausa(httpStatus.toString());

        obj.setPath(request.getContextPath() + request.getServletPath());
        obj.setData(LocalDateTime.now());

        e.printStackTrace();

        return  ResponseEntity.status(httpStatus).body(obj);
    }
}
