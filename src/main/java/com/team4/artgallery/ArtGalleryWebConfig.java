package com.team4.artgallery;

import com.team4.artgallery.controller.resolver.LoginMemberArgumentResolver;
import com.team4.artgallery.util.PaginationConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class ArtGalleryWebConfig implements WebMvcConfigurer {

    private final LoginMemberArgumentResolver loginMemberArgumentResolver;
    private final PaginationConverter paginationConverter;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginMemberArgumentResolver);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(paginationConverter);
    }

}