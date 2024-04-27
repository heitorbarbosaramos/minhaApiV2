package com.heitor.minhaApi.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.ExternalDocumentation;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(type = SecuritySchemeType.HTTP, name = "BearerJWT", scheme = "bearer", bearerFormat = "JWT", description = "Bearer Token para o projeto.")
@OpenAPIDefinition(servers =  {	@Server(url = "http://localhost:8080/rest"),	@Server(url = "http://outroservidor:9090")})
public class SwaggerConfig {

    @Value("${spring.application.name}")
    private String nomeApi;

    @Value("${spring.application.description}")
    private String descricao;

    @Value("${info.app.version}")
    private String versao;

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("com.sisweb.api")
                .packagesToScan("com.heitor.minhaApi.web.rest", "com.heitor.minhaApi.security")
                .build();
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info().title("NOME DA API: " + nomeApi)
                        .description(descricao)
                        .contact(new Contact()
                                .name("SisWeb")
                                .url("https://github.com/heitorbarbosaramos/minhaApiV2")
                                .email("heitorhfbr@gmail.com"))
                        .version(versao)
                        .license(new License().name("Versão: " + versao).url("https://github.com/heitorbarbosaramos/minhaApiV2")))
                .externalDocs(new ExternalDocumentation().description("Para mais esclarecimentos")
                        .url("https://github.com/heitorbarbosaramos/minhaApiV2"));
    }
}
