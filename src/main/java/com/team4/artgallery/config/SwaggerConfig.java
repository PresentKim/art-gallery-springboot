package com.team4.artgallery.config;

import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi artworksApi() {
        return GroupedOpenApi.builder().group("artwork")
                .packagesToScan("com.team4.artgallery.controller.domain.artwork")
                .addOpenApiCustomizer(openApi -> openApi.info(new Info().title("Artwork API").version("v1")))
                .build();
    }

    @Bean
    public GroupedOpenApi galleriesApi() {
        return GroupedOpenApi.builder().group("gallery")
                .packagesToScan("com.team4.artgallery.controller.domain.gallery")
                .addOpenApiCustomizer(openApi -> openApi.info(new Info().title("Gallery API").version("v1")))
                .build();
    }

    @Bean
    public GroupedOpenApi favoriteApi() {
        return GroupedOpenApi.builder().group("favorite")
                .packagesToScan("com.team4.artgallery.controller.domain.favorite")
                .addOpenApiCustomizer(openApi -> openApi.info(new Info().title("Favorite API").version("v1")))
                .build();
    }

    @Bean
    public GroupedOpenApi memberApi() {
        return GroupedOpenApi.builder().group("member")
                .packagesToScan("com.team4.artgallery.controller.domain.member")
                .addOpenApiCustomizer(openApi -> openApi.info(new Info().title("Member API").version("v1")))
                .build();
    }

    @Bean
    public GroupedOpenApi noticeApi() {
        return GroupedOpenApi.builder().group("notice")
                .packagesToScan("com.team4.artgallery.controller.domain.notice")
                .addOpenApiCustomizer(openApi -> openApi.info(new Info().title("Notice API").version("v1")))
                .build();
    }

    @Bean
    public GroupedOpenApi qnaApi() {
        return GroupedOpenApi.builder().group("qna")
                .packagesToScan("com.team4.artgallery.controller.domain.qna")
                .addOpenApiCustomizer(openApi -> openApi.info(new Info().title("QnA API").version("v1")))
                .build();
    }

    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder().group("admin")
                .packagesToScan("com.team4.artgallery.controller.domain.admin")
                .addOpenApiCustomizer(openApi -> openApi.info(new Info().title("Admin API").version("v1")))
                .build();
    }

}