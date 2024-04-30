package com.heitor.minhaApi.service.utils;

import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CorpoEmail {
    public static String criarConta(String nome, String email, LocalDateTime dataHora, String codVerificacao, String linkStep1){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");

        String corpoEmail = "Prezado " + nome;
        corpoEmail += "<br><br>";
        corpoEmail += "Foi solicitado a criação de conta para o email " + email;
        corpoEmail += "<br><br>";
        corpoEmail += "Segue abaixo o link e o código de verificação";
        corpoEmail += "<div class=\"row h-100 justify-content-center align-items-center jumbotron\">\n";
        corpoEmail += "   <p><a class=\"btn btn-primary btn-lg\" href='"+linkStep1+"' role=\"button\">ATIVAÇÃO - COD: " + codVerificacao + "</a></p>";
        corpoEmail += "</div>";
        corpoEmail += "<br><br>";
        corpoEmail += "<div class=\"well\">Caso o botão não funcione utilize o link: "+linkStep1+"</div>";
        corpoEmail += "<br><br>";
        corpoEmail += "O link estará disponivel até " + dataHora.format(formatter) + ", após esse horário a solicitação será desfeita e será necessário repetir o processo de criação.";
        corpoEmail += "<br><br>";

        return corpoEmail;
    }
}
