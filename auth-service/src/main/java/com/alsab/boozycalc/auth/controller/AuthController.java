package com.alsab.boozycalc.auth.controller;

import com.alsab.boozycalc.auth.security.UserDetailsServiceImpl;
import com.alsab.boozycalc.auth.security.jwt.JwtUtilsService;
import com.alsab.boozycalc.auth.security.payload.AuthenticationRequest;
import com.alsab.boozycalc.auth.security.payload.AuthenticationResponse;
import com.alsab.boozycalc.auth.security.payload.RegisterRequest;
import com.alsab.boozycalc.auth.security.payload.ValidationRequest;
import com.alsab.boozycalc.auth.service.AuthenticationService;
import com.alsab.boozycalc.auth.service.data.UserDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth controller", description = "Authentication and authorization controller")
public class AuthController {
    private final AuthenticationService authenticationService;
    private final JwtUtilsService jwtUtilsService;
    private final UserDetailsServiceImpl userDetailsService;
    private final UserDataService userDataService;

    @PostMapping("/register")
    @Operation(description = "Register new user",
            summary = "Register",
            tags = "auth")
    public Mono<ResponseEntity<AuthenticationResponse>> register(@RequestBody RegisterRequest request) {
        return authenticationService.register(request).map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().body(new AuthenticationResponse(e.getMessage()))));
    }

    @PostMapping("/authenticate")
    @Operation(description = "Authenticate new user",
            summary = "Authenticate",
            tags = "auth")
    public Mono<ResponseEntity<AuthenticationResponse>> authenticate(@RequestBody AuthenticationRequest request) {
        return authenticationService.authenticate(request).map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().body(new AuthenticationResponse(e.getMessage()))));
    }

    @PostMapping("/validate")
    @Operation(description = "Validate token",
            summary = "Validate",
            tags = "auth")
    public ResponseEntity<Boolean> validate(@RequestBody String token) {
        return ResponseEntity.ok(jwtUtilsService.validateJwtToken(token, userDetailsService.loadUserByUsername(jwtUtilsService.getUserNameFromJwtToken(token))));
    }

    @PostMapping("/do-filter")
    @Operation(description = "Filter token",
            summary = "Filter",
            tags = "auth")
    public Mono<ResponseEntity<Boolean>> filter(@RequestBody String token) {
        return Mono.just(jwtUtilsService.doFilterFromRequest(token)).map(ResponseEntity::ok);
    }

    @PostMapping("/get-login-from-token")
    @Operation(description = "Get user login from token",
            summary = "Get user login",
            tags = "auth")
    public ResponseEntity<String> getLoginFromToken(@RequestBody ValidationRequest request) {
        return ResponseEntity.ok(jwtUtilsService.getUserNameFromJwtToken(request.getJwt()));
    }

    @GetMapping("/find")
    @Operation(description = "Find user by id",
            summary = "Find user",
            tags = "auth")
    public ResponseEntity<?> findById(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(userDataService.findById(id).block());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

}
