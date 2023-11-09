package com.alsab.boozycalc.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartyDto {
    private Long id;
    @NotBlank(message = "Name is mandatory")
    @NotNull(message = "Name is mandatory")
    private String name;
    private java.sql.Timestamp event_date;
    private String location;
    private String description;
}
