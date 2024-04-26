package com.heitor.minhaApi.exceptions;

import feign.FeignException;
import feign.codec.DecodeException;
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
            obj.setMensagem("Usuário ou senha estão errados ou usuário não existe, ou a sessão expirou");
        }else if(e.getMessage().contains("403")) {
            httpStatus = HttpStatus.FORBIDDEN;
            obj.setMensagem("Usuário não tem permissão para acessar o recurso solicitado");
        } else if (e.getMessage().contains("400") && e.getMessage().contains("Account disabled")) {
            httpStatus = HttpStatus.FORBIDDEN;
            obj.setMensagem("Usuário desativado");
        } else if (e.getMessage().contains("409")) {
            httpStatus = HttpStatus.CONFLICT;
            obj.setMensagem("Já existe um usuário com o mesmo nome de usuário");
        } else if (e.getMessage().contains("400") && e.getMessage().contains("Invalid refresh token")) {
            httpStatus = HttpStatus.FORBIDDEN;
            obj.setMensagem("Token de acesso inválido");
        } else if (e.getMessage().contains("404") && e.getMessage().contains("updateUser")) {
            httpStatus = HttpStatus.NOT_FOUND;
            obj.setMensagem("Usuário não localizado");
        } else if (e.getMessage().contains("404") && e.getMessage().contains("Could not find group by id")) {
                httpStatus = HttpStatus.NOT_FOUND;
                obj.setMensagem("Não foi possível localizar o grupo por id");
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

    @ExceptionHandler(DecodeException.class)
    public ResponseEntity<MensagemPadrao> decodeException(DecodeException e, HttpServletRequest request){

        MensagemPadrao obj = new MensagemPadrao();

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        obj.setIdStatus(httpStatus.value());
        obj.setCausa(httpStatus.toString());
        obj.setMensagem(e.getMessage());
        obj.setPath(request.getContextPath() + request.getServletPath());
        obj.setData(LocalDateTime.now());

        e.printStackTrace();

        return  ResponseEntity.status(httpStatus).body(obj);
    }
}
