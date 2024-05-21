package com.heitor.minhaApi.security;

import com.heitor.minhaApi.security.feignClient.RealmAccess;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.util.List;

@Service
public class CookiesUtils {

    @Value("${cookies.name.token}")
    private String cookieNameToken;
    @Value("${cookies.name.token.refresh}")
    private String cookieNameTokenRefresh;
    @Value("${cookies.name.perfis}")
    private String perfisName;
    @Value("${cookies.domain}")
    private String cookieDomain;
    @Value("${cookies.path}")
    private String cookiePath;

    public void createCookieToken(HttpServletRequest request, HttpServletResponse response, String accessToken){

        Cookie cookie = new Cookie(cookieNameToken, accessToken);
        cookie.setPath(cookiePath);
        cookie.setHttpOnly(Boolean.FALSE);
        cookie.setDomain(cookieDomain);
        response.addCookie(cookie);
    }

    public void createCookiePerfil(HttpServletRequest request, HttpServletResponse response, List<String > roles) {

        if (roles.isEmpty()) {return;}

        String perfis = String.join("-", roles);

        Cookie cookie = new Cookie(perfisName, perfis);
        cookie.setPath(cookiePath);
        cookie.setHttpOnly(Boolean.FALSE);
        cookie.setDomain(cookieDomain);
        response.addCookie(cookie);
    }

    public void createCookieTokenRefresh(HttpServletRequest request, HttpServletResponse response, String refreshToken) {
        Cookie cookie = new Cookie(cookieNameTokenRefresh, refreshToken);
        cookie.setPath(cookiePath);
        cookie.setHttpOnly(Boolean.FALSE);
        cookie.setDomain(cookieDomain);
        response.addCookie(cookie);
    }

    public void revokeCookies(HttpServletRequest request, HttpServletResponse response) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                Cookie newCookie = new Cookie(cookie.getName(), "");
                newCookie.setMaxAge(0);
                newCookie.setPath(cookie.getPath() != null ? cookie.getPath() : "/");
                newCookie.setHttpOnly(cookie.isHttpOnly());
                newCookie.setSecure(cookie.getSecure());
                response.addCookie(newCookie);
            }
        }
    }
}
