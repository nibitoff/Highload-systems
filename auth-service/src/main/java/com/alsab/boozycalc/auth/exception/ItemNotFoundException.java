package com.alsab.boozycalc.auth.exception;

import lombok.Getter;

@Getter
public class ItemNotFoundException extends RuntimeException{
    private final Long id;
    private final Class<?> itemClass;
    private final String description;

    public ItemNotFoundException(Class<?> itemClass, Long id){
        this.id = id;
        this.itemClass = itemClass;
        this.description = String.format("No item of [" + itemClass.getSimpleName() + "] with id " + id);
    }
}
