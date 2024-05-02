package com.heitor.minhaApi.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


import java.util.*;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    @Value("${keycloak.client.server.url}")
    private String keycloakSereverUrl;
    @Value("${bucket.front.url}")
    private String bucketFront;
    private static final String[] PUBLIC_MATCHERS_POST = {
            "/user/login",
            "/user/refreshToken",
            "/usuario/create/step1",
            "/usuario/create/step3"
    };

    private static final String[] PUBLIC_MATCHERS_GET = {
            "/actuator/info",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/usuario/create/step2/**"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth->{
                    auth.requestMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST).permitAll();
                    auth.requestMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll();
                    auth.anyRequest().authenticated();
                })
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.decoder(jwtDecoder())));
        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder(){
        return NimbusJwtDecoder.withJwkSetUri(keycloakSereverUrl+"/protocol/openid-connect/certs").build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverterForKeyCloak(){
        Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter = jwt ->{
            Map<String, Object> resourcesAccess = jwt.getClaim("realm_access");
            if (resourcesAccess != null) {
                Object rolesObject = resourcesAccess.get("roles");
                if (rolesObject instanceof Collection) {
                    Collection<String> roles = (Collection<String>) rolesObject;
                    return roles.stream()
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());
                }
            }
            return Collections.emptyList();
        };
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(bucketFront));
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "OPTIONS","PATCH"));
        configuration.setExposedHeaders(Arrays.asList("x-auth-token", "x-requested-with", "x-xsrf-token","X-XSRF-TOKEN", "Access-Control-Allow-Origin: *"));
        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "X-Auth-Token","x-auth-token", "x-requested-with","X-XSRF-TOKEN"));
        configuration.setAllowCredentials(false);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
