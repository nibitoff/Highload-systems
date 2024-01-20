package com.alsab.boozycalc.gateway.security;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class JwtUtil {
    private final ReactorLoadBalancerExchangeFilterFunction lbFunction;

    public Mono<Boolean> isValid(String token) {
        return WebClient.builder()
                .filter(lbFunction)
                .build()
                .post()
                .uri("http://auth-service/api/v1/auth/do-filter")
                .bodyValue(token).retrieve().bodyToMono(Boolean.class);
    }

}