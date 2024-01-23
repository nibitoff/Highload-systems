package com.alsab.boozycalc.file.exception;

import lombok.Getter;

import java.io.IOException;

@Getter
public class FileOperationException extends RuntimeException{
    private final String description;

    public FileOperationException(IOException e){
        this.description = String.format("Got file exception " + e.getMessage());
    }
}
