package com.alsab.boozycalc.auth.service;

import com.alsab.boozycalc.auth.dto.UserDto;
import com.alsab.boozycalc.auth.entity.RoleEnum;
import com.alsab.boozycalc.auth.exception.UsernameIsAlreadyTakenException;
import com.alsab.boozycalc.auth.security.jwt.JwtUtilsService;
import com.alsab.boozycalc.auth.security.payload.AuthenticationRequest;
import com.alsab.boozycalc.auth.security.payload.AuthenticationResponse;
import com.alsab.boozycalc.auth.security.payload.RegisterRequest;
import com.alsab.boozycalc.auth.service.data.UserDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserDataService userDataService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtilsService jwtUtils;
    private final AuthenticationManager authenticationManager;

    public Mono<AuthenticationResponse> register(RegisterRequest req) {
        return userDataService.userExists(req.getUsername())
                .flatMap(
                        b -> {
                            if (b) {
                                return Mono.error(new UsernameIsAlreadyTakenException(req.getUsername()));
                            }
                            UserDto user = UserDto.builder()
                                    .id(null)
                                    .realName(req.getRealname())
                                    .username(req.getUsername())
                                    .password(passwordEncoder.encode(req.getPassword()))
                                    .role(RoleEnum.USER)
                                    .build();
                            return Mono.just(user);
                        }
                )
                .flatMap(userDataService::saveUser)
                .flatMap(u -> Mono.just(new AuthenticationResponse(jwtUtils.generateJwtToken(u))));
    }

    public Mono<AuthenticationResponse> authenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        } catch (Exception e) {
            return Mono.error(e);
        }
        return userDataService.findByUserName(request.getUsername()).flatMap(user -> {
            String jwt = jwtUtils.generateJwtToken(user);
            return Mono.just(AuthenticationResponse.builder().token(jwt).build());
        });
    }
}
