package com.alsab.boozycalc.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
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
    @NotNull(message = "Person is mandatory")
    private UserDto person;
}
