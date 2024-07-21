package com.heitor.minhaApi.service.whatsapp;

import com.heitor.minhaApi.feign.SendMessageRequest;
import com.heitor.minhaApi.feign.SendMessageResponse;
import com.heitor.minhaApi.feign.WhatsAppCliente;
import com.heitor.minhaApi.feign.WhatsAppSessionResponse;
import feign.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class WhatsAppService {

    private final WhatsAppCliente appCliente;

    @Value("${api.whatsapp.session}")
    private String sessionApi;
    @Value("${api.whatsapp.token}")
    private String tokenApiWhatsApp;

    public WhatsAppSessionResponse criarSession(String session){
        if(session.isEmpty()){
            session = sessionApi;
        }
        return appCliente.createSession(tokenApiWhatsApp, session);
    }

    public WhatsAppSessionResponse recoveryStatusSession(String session){
        session = verifySession(session);
        return appCliente.recoveryStatusSession(tokenApiWhatsApp, session);
    }

    public WhatsAppSessionResponse recoveryCodeQr(String session){
        if(session.isEmpty()){
            session = sessionApi;
        }
        return appCliente.recoveryCodeQr(tokenApiWhatsApp, session);
    }

    public byte[] recoveryImagemPngQRCode(String session) throws IOException {
        session = verifySession(session);
        Response response = appCliente.qrCodeWhatsAppSession(tokenApiWhatsApp, session);
        return response.body().asInputStream().readAllBytes();
    }

    public SendMessageResponse sendMessage(String session, SendMessageRequest response){
        session = verifySession(session);
        String codDDD = response.getChatId().substring(0, 2);
        String numero = response.getChatId().replaceAll("[()\\- ]", "").substring(2);
        int quantNumero = numero.length();

        List<CodDDDRepresentation> list = CodigosDDD.ddd().stream().filter(f-> {
            return Objects.equals(f.getDdd(), codDDD);
        }).toList();

        if(list.isEmpty()){
            throw new IllegalArgumentException("Código DDD inválido: " + codDDD);
        }

        if(quantNumero == 8 || quantNumero == 9){

            if(quantNumero == 9){
                char firstNumber = numero.charAt(0);
                if(firstNumber == '9'){
                    numero = numero.substring(1);
                }
            }

        }else {
            throw new IllegalArgumentException("Número de contato não contem quantidade de caracteres suficente para ser um número válido");
        }

        String tel = "55" + codDDD + numero + "@c.us";
        response.setChatId(tel);
        return appCliente.sendMessage(tokenApiWhatsApp, session, response);
    }

    private String verifySession(String session){
        if(session.isEmpty()){
            session = sessionApi;
        }
        return session;
    }
}
