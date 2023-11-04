package com.alsab.boozycalc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartyDto {
    private Long id;
    private String name;
    private java.sql.Timestamp event_date;
    private String location;
    private String description;
}
