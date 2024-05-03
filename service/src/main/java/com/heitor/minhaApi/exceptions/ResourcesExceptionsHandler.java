package com.heitor.minhaApi.exceptions;

import feign.FeignException;
import feign.codec.DecodeException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;
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

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<MensagemPadrao> SQLException(SQLException e, HttpServletRequest request){

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

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<MensagemPadrao> illegalArgumentException(IllegalArgumentException e, HttpServletRequest request){

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

    @ExceptionHandler(MailException.class)
    public ResponseEntity<MensagemPadrao> mailException(MailException e, HttpServletRequest request){

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

    @ExceptionHandler(StackOverflowError.class)
    public ResponseEntity<MensagemPadrao> stackOverflowError(StackOverflowError e, HttpServletRequest request){

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

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<MensagemPadrao> nullPointerException(NullPointerException e, HttpServletRequest request){

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrosLista> validation(MethodArgumentNotValidException e, HttpServletRequest request){

        ErrosLista obj = new ErrosLista();

        obj.setIdStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
        obj.setCausa(HttpStatus.UNPROCESSABLE_ENTITY.toString());
        obj.setMensagem("Erro de validação");
        obj.setPath(request.getContextPath() + request.getServletPath());
        obj.setData(LocalDateTime.now());

        for(FieldError x : e.getBindingResult().getFieldErrors()){
            obj.getErros().add(new ErrosCampos(x.getField(), x.getDefaultMessage()));
        }
        e.printStackTrace();
        return  ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(obj);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<MensagemPadrao> constrainViolation(ConstraintViolationException e, HttpServletRequest request){

        HttpStatus httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;


        ErrosLista obj = new ErrosLista();
        obj.setIdStatus(httpStatus.value());
        obj.setCausa(httpStatus.toString());
        obj.setMensagem("Erro de validação.");
        obj.setPath(request.getContextPath() + request.getServletPath());
        obj.setData(LocalDateTime.now());

        for(ConstraintViolation error : e.getConstraintViolations()){
            obj.getErros().add(new ErrosCampos(error.getPropertyPath().toString(), error.getMessage() ));
        }

        e.printStackTrace();

        return  ResponseEntity.status(httpStatus).body(obj);
    }
}
