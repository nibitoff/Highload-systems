package com.alsab.boozycalc.service;

import com.alsab.boozycalc.dto.UserDto;
import com.alsab.boozycalc.entity.RoleEnum;
import com.alsab.boozycalc.exception.UsernameIsAlreadyTakenException;
import com.alsab.boozycalc.security.jwt.JwtUtilsService;
import com.alsab.boozycalc.security.payload.AuthenticationRequest;
import com.alsab.boozycalc.security.payload.AuthenticationResponse;
import com.alsab.boozycalc.security.payload.RegisterRequest;
import com.alsab.boozycalc.service.data.UserDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserDataService userDataService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtilsService jwtUtils;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request){
        if(userDataService.userExists(request.getUsername())) throw new UsernameIsAlreadyTakenException(request.getUsername());

        UserDto user = UserDto.builder()
                .realName(request.getRealname())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(RoleEnum.USER)
                .build();
        userDataService.saveUser(user);
        String jwt = jwtUtils.generateJwtToken(user);
        return AuthenticationResponse.builder().token(jwt).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        UserDto user = userDataService.findByUserName(request.getUsername());
        String jwt = jwtUtils.generateJwtToken(user);
        return AuthenticationResponse.builder().token(jwt).build();
    }
}
