package com.alsab.boozycalc.gateway.security;

import com.alsab.boozycalc.gateway.feign.AuthServiceFeignClient;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
@ConditionalOnClass(HttpMessageConverter.class)
public class JwtUtil {
    private final CircuitBreaker thisCircuitBreaker;
    private final AuthServiceFeignClient authServiceFeignClient;

    public JwtUtil(CircuitBreaker thisCircuitBreaker, @Lazy AuthServiceFeignClient authServiceFeignClient) {
        this.thisCircuitBreaker = thisCircuitBreaker;
        this.authServiceFeignClient = authServiceFeignClient;
    }


    //eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtaW1pYyIsImlhdCI6MTcwNDgzMDQ1OSwiZXhwIjoxNzA0ODMyMjU5fQ.KEr_OYhZKA6XHGG66-Yu1SRrFGQwjswGOecDImLnLw8
    public boolean isValid(String token) {
        return thisCircuitBreaker.decorateSupplier(() -> authServiceFeignClient.doFilter(token)).get();
    }

}