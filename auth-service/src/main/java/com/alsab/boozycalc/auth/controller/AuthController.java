package com.alsab.boozycalc.auth.controller;

import com.alsab.boozycalc.auth.exception.UsernameIsAlreadyTakenException;
import com.alsab.boozycalc.auth.security.UserDetailsServiceImpl;
import com.alsab.boozycalc.auth.security.jwt.JwtUtilsService;
import com.alsab.boozycalc.auth.security.payload.AuthenticationRequest;
import com.alsab.boozycalc.auth.security.payload.AuthenticationResponse;
import com.alsab.boozycalc.auth.security.payload.RegisterRequest;
import com.alsab.boozycalc.auth.security.payload.ValidationRequest;
import com.alsab.boozycalc.auth.service.AuthenticationService;
import com.alsab.boozycalc.auth.service.data.UserDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthenticationService authenticationService;
    private final JwtUtilsService jwtUtilsService;
    private final UserDetailsServiceImpl userDetailsService;
    private final UserDataService userDataService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            return ResponseEntity.ok(authenticationService.register(request));
        } catch (UsernameIsAlreadyTakenException e){
            return ResponseEntity.badRequest().body(Mono.error(e));
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/validate")
    public ResponseEntity<Boolean> validate(@RequestBody String token) {
        return ResponseEntity.ok(jwtUtilsService.validateJwtToken(token, userDetailsService.loadUserByUsername(jwtUtilsService.getUserNameFromJwtToken(token))));
    }

    @PostMapping("/do-filter")
    public ResponseEntity<Boolean> filter(@RequestBody String token) {
        return ResponseEntity.ok(jwtUtilsService.doFilterFromRequest(token));
    }

    @PostMapping("/get-login-from-token")
    public ResponseEntity<String> getLoginFromToken(@RequestBody ValidationRequest request) {
        return ResponseEntity.ok(jwtUtilsService.getUserNameFromJwtToken(request.getJwt()));
    }

    @GetMapping("/find")
    public ResponseEntity<?> findById(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(userDataService.findById(id).block());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

}
