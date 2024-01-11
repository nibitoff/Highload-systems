package com.alsab.boozycalc.party.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntryDto {
    @NotNull(message = "Order is mandatory")
    private OrderDto order;
    @NotNull(message = "Cocktail is mandatory")
    private CocktailDto cocktail;
    @NotNull(message = "Name is mandatory")
    private String name;
    @NotNull(message = "Quantity is mandatory")
    @Positive(message = "Quantity must be positive")
    private int quantity;
    @NotNull(message = "PriceForOne is mandatory")
    @Positive(message = "PriceForOne must be positive")
    private float priceForOne;
}
