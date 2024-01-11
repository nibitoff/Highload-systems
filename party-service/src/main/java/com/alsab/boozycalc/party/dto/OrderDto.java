package com.alsab.boozycalc.party.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private Long id;
    @NotNull(message = "Price is mandatory")
    @Positive(message = "Price must be positive")
    private float price;
    @NotNull(message = "Party is mandatory")
    private PartyDto party;
}
