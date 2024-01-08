package com.alsab.boozycalc.party.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseDto {
    @NotNull(message = "Product is mandatory")
    private ProductDto product;
    @NotNull(message = "Party is mandatory")
    private PartyDto party;
    @Positive(message = "Quantity must be positive")
    private float quantity;

}
