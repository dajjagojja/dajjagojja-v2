package com.multi.travel.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

/**
 * Please explain the class!!!
 *
 * @author : Choi MinHyeok
 * @filename : OpenApiConfig
 * @since : 26. 2. 2. 월요일
 **/


@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Travel API",
                description = "Travel Service API Documentation",
                version = "v2"
        ),
        security = @SecurityRequirement(name = "BearerAuth")
)
@SecurityScheme(
        name = "BearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class OpenApiConfig {
}