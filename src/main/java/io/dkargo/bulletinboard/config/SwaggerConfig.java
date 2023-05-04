package io.dkargo.bulletinboard.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@ConditionalOnExpression(value = "${swagger.enable:false}")
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        String basePackage = "io.dkargo.bulletinboard.controller";

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.any())
                .build();
    }

    public ApiInfo apiInfo() {
        String apiDescription = "게시판 API";
        String apiVersion = "0.0.1";
        String apiTitle = "Board API";

        return new ApiInfoBuilder()
                .title(apiTitle)
                .version(apiVersion)
                .description(apiDescription)
                .build();
    }
}