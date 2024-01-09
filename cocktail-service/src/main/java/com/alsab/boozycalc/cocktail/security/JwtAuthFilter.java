package com.alsab.boozycalc.cocktail.security;

import com.alsab.boozycalc.cocktail.feign.AuthServiceFeignClient;
import com.alsab.boozycalc.cocktail.security.payload.ValidationRequest;
import com.alsab.boozycalc.cocktail.security.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;



import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final CircuitBreaker countCircuitBreaker;
    private final AuthServiceFeignClient loginServiceFeignClient;
    private final UserDetailsServiceImpl userDetailsService;


    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);
        username = getUserNameFromJwtToken(jwt);

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if(validateJwtToken(jwt, username)) {
                UserDetails userDetails = getUserDetails(username);
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }

    private String getUserNameFromJwtToken(String jwt){
        return countCircuitBreaker.decorateSupplier(() ->
                loginServiceFeignClient.getLoginFromToken(ValidationRequest.builder().jwt(jwt).build()).getBody()).get();
    }

    private UserDetails getUserDetails(String username){
        return userDetailsService.loadUserByUsername(username);
    }

    private boolean validateJwtToken(String jwt, String username){
        return countCircuitBreaker.decorateSupplier(()->(boolean)
                loginServiceFeignClient.validateJwtToken(ValidationRequest.builder().jwt(jwt).username(username).build()).getBody()).get();
    }
}
