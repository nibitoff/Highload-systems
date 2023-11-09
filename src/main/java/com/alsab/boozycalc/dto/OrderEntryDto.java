package com.alsab.boozycalc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntryDto {
    private OrderDto order;
    private CocktailDto cocktail;
    private String name;
    private int quantity;
    private float priceForOne;
}
