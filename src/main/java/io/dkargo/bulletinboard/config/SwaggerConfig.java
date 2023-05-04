package io.dkargo.bulletinboard.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
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
)
@Configuration
public class SwaggerConfig {

//
//    @Bean
//    public Docket api() {
//        String basePackage = "io.dkargo.bulletinboard.controller";
//
//        return new Docket(DocumentationType.OAS_30)
//                .consumes(getConsumeContentTypes())
//                .produces(getProduceContentTypes())
//                .apiInfo(apiInfo())
//                .select()
//                .apis(RequestHandlerSelectors.basePackage(basePackage))
//                .paths(PathSelectors.any())
//                .build()
//                .useDefaultResponseMessages(false);
//    }
//
//    public ApiInfo apiInfo() {
//        String apiDescription = "게시판 API";
//        String apiVersion = "0.0.1";
//        String apiTitle = "Board API";
//
//        return new ApiInfoBuilder()
//                .title(apiTitle)
//                .version(apiVersion)
//                .description(apiDescription)
//                .build();
//    }
//
//    private Set<String> getConsumeContentTypes() {
//        Set<String> consumes = new HashSet<>();
//        consumes.add("application/json;charset=UTF-8");
//        consumes.add("application/x-www-form-urlencoded");
//        consumes.add("multipart/form-data");
//        return consumes;
//    }
//
//    private Set<String> getProduceContentTypes() {
//        Set<String> produces = new HashSet<>();
//        produces.add("application/json;charset=UTF-8");
//        return produces;
//    }
}