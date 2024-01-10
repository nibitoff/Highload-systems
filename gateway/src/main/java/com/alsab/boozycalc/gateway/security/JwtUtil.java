package com.alsab.boozycalc.gateway.security;

import com.alsab.boozycalc.gateway.feign.AuthServiceFeignClient;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnClass(HttpMessageConverter.class)
public class JwtUtil {
    private final CircuitBreaker thisCircuitBreaker;
    private final AuthServiceFeignClient authServiceFeignClient;

    public JwtUtil(CircuitBreaker thisCircuitBreaker, @Lazy AuthServiceFeignClient authServiceFeignClient) {
        this.thisCircuitBreaker = thisCircuitBreaker;
        this.authServiceFeignClient = authServiceFeignClient;
    }


    public boolean isValid(String token) {
        return thisCircuitBreaker.decorateSupplier(() -> authServiceFeignClient.doFilter(token)).get();
    }

}