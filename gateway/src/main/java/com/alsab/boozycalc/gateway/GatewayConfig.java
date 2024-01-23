package com.alsab.boozycalc.gateway;

import com.alsab.boozycalc.gateway.security.GatewayJwtFilter;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

@Configuration
public class GatewayConfig {
    @Autowired
    GatewayJwtFilter gatewayJwtFilter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth-service", r -> r.path("/auth/**")
                        .filters(f -> f.prefixPath("/api/v1"))
                        .uri("lb://auth-service"))
                .route("party-service-purchases", r -> r.path("/purchases/**")
                        .filters(f -> f.prefixPath("/api/v1").filter(gatewayJwtFilter))
                        .uri("lb://party-service"))
                .route("party-service-menus", r -> r.path("/menus/**")
                        .filters(f -> f.prefixPath("/api/v1").filter(gatewayJwtFilter))
                        .uri("lb://party-service"))
                .route("party-service-parties", r -> r.path("/parties/**")
                        .filters(f -> f.prefixPath("/api/v1").filter(gatewayJwtFilter))
                        .uri("lb://party-service"))
                .route("party-service-orders", r -> r.path("/orders/**")
                        .filters(f -> f.prefixPath("/api/v1").filter(gatewayJwtFilter))
                        .uri("lb://party-service"))
                .route("party-service-invites", r -> r.path("/invites/**")
                        .filters(f -> f.prefixPath("/api/v1").filter(gatewayJwtFilter))
                        .uri("lb://party-service"))
                .route("cocktail-service-invites", r -> r.path("/cocktails/**")
                        .filters(f -> f.prefixPath("/api/v1").filter(gatewayJwtFilter))
                        .uri("lb://cocktail-service"))
                .route("cocktail-service-invites", r -> r.path("/ingredients/**")
                        .filters(f -> f.prefixPath("/api/v1").filter(gatewayJwtFilter))
                        .uri("lb://cocktail-service"))
                .route("cocktail-service-invites", r -> r.path("/products/**")
                        .filters(f -> f.prefixPath("/api/v1").filter(gatewayJwtFilter))
                        .uri("lb://cocktail-service"))
                .route("cocktail-service-invites", r -> r.path("/recipes/**")
                        .filters(f -> f.prefixPath("/api/v1").filter(gatewayJwtFilter))
                        .uri("lb://cocktail-service"))
                .build();
    }

    @Bean
    @ConditionalOnMissingBean
    public HttpMessageConverters messageConverters(ObjectProvider<HttpMessageConverter<?>> converters) {
        return new HttpMessageConverters(converters.orderedStream().collect(Collectors.toList()));
    }

    @Bean
    public CorsWebFilter corsWebFilter() {

        final CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOrigins(Collections.singletonList("*"));
        corsConfig.setMaxAge(3600L);
        corsConfig.setAllowedMethods(Arrays.asList("GET", "POST"));
        corsConfig.addAllowedHeader("*");

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return new CorsWebFilter(source);
    }
}
