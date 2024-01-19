package com.alsab.boozycalc.auth.exception;

import lombok.Getter;

@Getter
public class UsernameIsAlreadyTakenException extends RuntimeException {
    public UsernameIsAlreadyTakenException(String username){
        super("Username '" + username + "' is already taken");
    }
}
