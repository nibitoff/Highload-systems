package com.alsab.boozycalc.cocktail.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Long id;
    @NotBlank(message = "Name is mandatory")
    @NotNull(message = "Name is mandatory")
    private String name;
    private String description;
    @NotNull(message = "Ingredient is mandatory")
    private IngredientDto ingredient;
    @Positive(message = "Price must be positive")
    @NotNull(message = "Price is mandatory")
    private float price;
}
