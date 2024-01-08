package com.alsab.boozycalc.party.exception;

public class FeignClientException extends RuntimeException{
    private final String description;

    public FeignClientException(String code, String serverName, String msg){
        this.description = String.format("Got code " + code + " from " + serverName + ": " + msg);
    }
}
