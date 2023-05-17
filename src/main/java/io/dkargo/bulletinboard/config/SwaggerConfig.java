package io.dkargo.bulletinboard.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Configuration;

@ConditionalOnExpression(value = "${swagger.enable:false}")
@OpenAPIDefinition(
        info = @Info(
                title = "게시판 API 명세서",
                description = "API 명세서",
                version = "v0.0.1",
                contact = @Contact(
                        name = "jhpark",
                        email = "jhpark@dkargo.io"
                )
        )
        ,
        security = {
                @SecurityRequirement(name = "serverName"),
                @SecurityRequirement(name = "key")
        }
)
@SecuritySchemes(value = {
        @SecurityScheme(name = "serverName",
                type = SecuritySchemeType.HTTP,
                scheme = "bearer",
                bearerFormat = "JWT",
                in = SecuritySchemeIn.HEADER,
                paramName = "Authorization",
                description = " jwt token"),
})

@Configuration
public class SwaggerConfig {
}

