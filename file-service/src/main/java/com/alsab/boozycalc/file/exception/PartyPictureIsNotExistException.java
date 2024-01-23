package com.alsab.boozycalc.file.exception;

import lombok.Getter;

@Getter
public class PartyPictureIsNotExistException extends RuntimeException{
    private final String fileName;
    private final String description;

    public PartyPictureIsNotExistException(String fileName){
        this.fileName = fileName;
        this.description = String.format("No picture of party with id" + fileName);
    }
}
