package com.heitor.minhaApi.service.utils;

import java.util.Random;

public class StringUtils {

    public static String geradorCaracteresNumericos(int quantCaracteres){
        StringBuilder gerado = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i < quantCaracteres; i++){
            gerado.append(random.nextInt(10)); // Ajuste para gerar apenas dígitos numéricos
        }
        return gerado.toString();
    }
}
