package io.dkargo.bulletinboard.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.crypto.password.PasswordEncoder;

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
                type = SecuritySchemeType.APIKEY,
                in = SecuritySchemeIn.HEADER,
                paramName = "server.name",
                description = "server name to authenticate"),
        @SecurityScheme(name = "key",
                type = SecuritySchemeType.APIKEY,
                in = SecuritySchemeIn.HEADER,
                paramName = "server.key",
                description = "server key to authenticate")
})

@Configuration
public class SwaggerConfig {


//    @Bean
//    public GroupedOpenApi SecurityGroupOpenApi(@Value("${spring.profiles.active}") String active) {
//        return GroupedOpenApi
//                .builder()
//                .group("Security Open Api")
//                .pathsToExclude("/auth/*", "/")
//                .addOpenApiCustomiser(buildSecurityOpenApi(active))
//                .build();
//    }
//    @Bean
//    public GroupedOpenApi NonSecurityGroupOpenApi(@Value("${spring.profiles.active}") String active) {
//        return GroupedOpenApi
//                .builder()
//                .group("Non Security Open Api")
//                .pathsToMatch("/auth/*")
//                .build();
//    }
//
//    public OpenApiCustomiser buildSecurityOpenApi(String active) {
//        SecurityScheme securityScheme = new SecurityScheme()
//                .name("Authorization")
//                .type(SecurityScheme.Type.HTTP)
//                .in(SecurityScheme.In.HEADER)
//                .bearerFormat("JWT")
//                .scheme("bearer");
//
//        return OpenApi -> OpenApi
//                .addSecurityItem(new SecurityRequirement().addList("jwt token"))
//                .getComponents().addSecuritySchemes("jwt token", securityScheme);
//    }

}

