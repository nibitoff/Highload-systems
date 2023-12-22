package com.alsab.boozycalc.auth.security.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ValidationRequest {
    String jwt;
    String username;
}
