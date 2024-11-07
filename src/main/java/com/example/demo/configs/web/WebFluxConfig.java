package com.example.demo.configs.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.ReactivePageableHandlerMethodArgumentResolver;
import org.springframework.data.web.ReactiveSortHandlerMethodArgumentResolver;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.result.method.annotation.ArgumentResolverConfigurer;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;

import com.example.demo.configs.web.deserializers.GrantedAuthorityDeserializer;

@Configuration
@EnableWebFlux
public class WebFluxConfig implements WebFluxConfigurer {

    public static final String PAGE_INITIAL = "1";

    public static final String PAGE_SIZE_DEFAULT = "20";
    public static final String MAX_PAGE_SIZE = "100";

    public static final String PAGE_PARAM_NAME = "page";
    public static final String SIZE_PARAM_NAME = "size";
    public static final String SORT_PARAM_NAME = "sort";

    private Sort defaultSort() {
        return Sort.by(Sort.Direction.ASC, "id");
    }

    private Pageable defaultPageRequest() {
        return PageRequest.ofSize(Integer.parseInt(PAGE_SIZE_DEFAULT));
    }

    @Override
    public void configureArgumentResolvers(@NonNull ArgumentResolverConfigurer configurer) {
        ReactiveSortHandlerMethodArgumentResolver sortHandlerMethodArgumentResolver = new ReactiveSortHandlerMethodArgumentResolver();
        sortHandlerMethodArgumentResolver.setSortParameter(SORT_PARAM_NAME);
        sortHandlerMethodArgumentResolver.setFallbackSort(defaultSort());
        ReactivePageableHandlerMethodArgumentResolver pageableHandlerMethodArgumentResolver = new ReactivePageableHandlerMethodArgumentResolver(
                sortHandlerMethodArgumentResolver);
        pageableHandlerMethodArgumentResolver.setPageParameterName(PAGE_PARAM_NAME);
        pageableHandlerMethodArgumentResolver.setSizeParameterName(SIZE_PARAM_NAME);
        pageableHandlerMethodArgumentResolver.setMaxPageSize(Integer.parseInt(MAX_PAGE_SIZE));
        pageableHandlerMethodArgumentResolver.setFallbackPageable(defaultPageRequest());
        pageableHandlerMethodArgumentResolver.setOneIndexedParameters(true);
        configurer.addCustomResolver(pageableHandlerMethodArgumentResolver);
    }

    @Bean
    Module grantedAuthorityModule() {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(GrantedAuthority.class, new GrantedAuthorityDeserializer());
        return module;
    }

}
