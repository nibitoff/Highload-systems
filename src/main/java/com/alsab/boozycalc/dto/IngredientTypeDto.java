package com.alsab.boozycalc.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mapstruct.Mapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngredientTypeDto {
    private Long id;
    private String name;
}
