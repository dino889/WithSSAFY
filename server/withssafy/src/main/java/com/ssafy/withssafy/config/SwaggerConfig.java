package com.ssafy.withssafy.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig{
    private String version;
    private String title;

    @Bean
    public Docket apiV1() {
        version = "V1";
        title = "WITHSSAFY API " + version;

        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .host("k6d201.p.ssafy.io")
                .groupName(version)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ssafy.withssafy.api"))
                .paths(PathSelectors.ant("/**"))
                .build()
                .apiInfo(apiInfo(title, version));

    }

    private ApiInfo apiInfo(String title, String version) {
        return new ApiInfo(
                title,
                "WITHSSAFY API",
                version,
                "www.example.com",
                new Contact("Contact Me", "www.example.com", "test@example.com"),
                "Licenses",
                "www.example.com",
                new ArrayList<>());
    }
}
