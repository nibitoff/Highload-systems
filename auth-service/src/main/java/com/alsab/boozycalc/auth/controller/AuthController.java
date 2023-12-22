package com.alsab.boozycalc.auth.controller;

import com.alsab.boozycalc.auth.security.UserDetailsServiceImpl;
import com.alsab.boozycalc.auth.security.jwt.JwtUtilsService;
import com.alsab.boozycalc.auth.security.payload.AuthenticationRequest;
import com.alsab.boozycalc.auth.security.payload.AuthenticationResponse;
import com.alsab.boozycalc.auth.security.payload.RegisterRequest;
import com.alsab.boozycalc.auth.security.payload.ValidationRequest;
import com.alsab.boozycalc.auth.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthenticationService authenticationService;
    private final JwtUtilsService jwtUtilsService;
    private final UserDetailsServiceImpl userDetailsService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/validate")
    public ResponseEntity<Boolean> validate(@RequestBody ValidationRequest request) {
        return ResponseEntity.ok(jwtUtilsService.validateJwtToken(request.getJwt(), userDetailsService.loadUserByUsername(request.getUsername())));
    }
}
