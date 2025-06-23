package com.edutech.courses.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.*;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("EduTech API")
                .version("1.0")
                .description("Microservicio de gestión académica de cursos")
                .contact(new Contact()
                    .name("Eduardo Uribe")
                    .email("eduardo@ejemplo.cl"))
                .license(new License().name("MIT").url("https://opensource.org/licenses/MIT")))
            .addServersItem(new Server().url("http://localhost:8080"));
    }
}
