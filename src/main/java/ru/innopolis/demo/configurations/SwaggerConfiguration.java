package ru.innopolis.demo.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * This class configure swagger documentation for the API
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    @Bean

    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).
                ignoredParameterTypes(AuthenticationPrincipal.class)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }
}
