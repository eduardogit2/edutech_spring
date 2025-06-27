package com.edutech.courses.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.*;
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
                    .name("Edutech team")
                    .email("edutech@ejemplo.cl")));
    }
}
