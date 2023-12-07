package com.alsab.boozycalc.auth.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {
    USER_READ("user:read"),
    ADMIN_READ("admin:read");

    @Getter
    private final String permission;
}
