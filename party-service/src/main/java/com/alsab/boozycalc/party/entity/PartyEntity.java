package com.alsab.boozycalc.party.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Table(name = "parties")
@Data
public class PartyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name", unique = true)
    private String name;
    @Column(name = "event_date")
    private Timestamp event_date;
    @Column(name = "location")
    private String location;
    @Column(name = "description")
    private String description;
}