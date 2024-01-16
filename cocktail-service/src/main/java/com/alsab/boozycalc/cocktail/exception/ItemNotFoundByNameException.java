package com.alsab.boozycalc.cocktail.exception;

import lombok.Getter;

@Getter
public class ItemNotFoundByNameException extends RuntimeException {
    private final String description;

    public ItemNotFoundByNameException(Class<?> itemClass, String name) {
        this.description = String.format("No item [" + itemClass.getSimpleName() + "] with name \"" + name + "\"");
    }
}
