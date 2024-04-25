package com.heitor.minhaApi.security.utils;

import jakarta.servlet.http.HttpServletRequest;

public class TokenUtils {

    public static String RetrieveToken(HttpServletRequest request){
        return request.getHeader("Authorization");
    }
}
