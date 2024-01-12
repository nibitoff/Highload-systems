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
//                        .uri("http://localhost:8183"))
                .build();
    }

    @Bean
    @ConditionalOnMissingBean
    public HttpMessageConverters messageConverters(ObjectProvider<HttpMessageConverter<?>> converters) {
        return new HttpMessageConverters(converters.orderedStream().collect(Collectors.toList()));
    }

}
