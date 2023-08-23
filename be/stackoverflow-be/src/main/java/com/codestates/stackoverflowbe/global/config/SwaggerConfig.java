package com.codestates.stackoverflowbe.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


//@OpenAPIDefinition(
//        info = @Info(title = "StackOverFlow API 명세서",
//        description = "스택오버플로우 클론 프로젝트 API 명세서",
//        version = "v1.0.0")
//)
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi groupedOpenApi() {
        String[] paths = {"/v1/**"};

        return GroupedOpenApi.builder()
                .group("7-Eleven Team")
                .pathsToMatch(paths)
                .build();
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .version("v1.0.0")
                .title("StackOverFlow API Specification")
                .description("@Author | Smile:DK, DoHyungK ")
                .contact(new Contact()
                        .name("DoKyung-Hwang")
                        .email("hdokyung94@gmail.com")
                        .url("https://github.com/Dokyung-Hwang"))
                .license(new License()
                        .name("Apache License 2.0")
                        .url("http://www.apache.org/licenses/LICENSE-2.0"));
    }


}
