package com.alsab.boozycalc.auth.exception;

import lombok.Getter;

@Getter
public class UsernameIsAlreadyTakenException extends RuntimeException {
    private final String descr;

    public UsernameIsAlreadyTakenException(String username){
        this.descr = String.format("Username \"" + username + "\" is already taken");
    }
}
