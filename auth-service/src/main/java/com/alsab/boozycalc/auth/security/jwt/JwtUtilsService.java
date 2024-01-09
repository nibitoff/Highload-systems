package com.alsab.boozycalc.auth.security.jwt;


import com.alsab.boozycalc.auth.security.UserDetailsServiceImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Log
public class JwtUtilsService {
    private final UserDetailsServiceImpl userDetailsService;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private int jwtExpirationMs;

    public String generateJwtToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return generateJwtToken(userDetails);
    }

    public String generateJwtToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date((System.currentTimeMillis())))
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody().getSubject();
    }

    public boolean doFilterFromRequest(String jwt){
        final String username;

        try {
            username = getUserNameFromJwtToken(jwt);
        } catch (io.jsonwebtoken.security.SignatureException | io.jsonwebtoken.ExpiredJwtException e){
            return false;
        }

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if(validateJwtToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
                return true;
            }
            return false;
        }
        return true;
    }

    public boolean validateJwtToken(String authToken, UserDetails userDetails) {
        try {
            Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(authToken);
            return getUserNameFromJwtToken(authToken).equals(userDetails.getUsername());
        } catch (MalformedJwtException e) {
            log.info("Invalid JWT token: {" + e + "}");
        } catch (ExpiredJwtException e) {
            log.info("JWT token is expired: {" + e + "}");
        } catch (UnsupportedJwtException e) {
            log.info("JWT token is unsupported: {" + e + "}");
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty: {" + e + "}");
        }
        return false;
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
