package com.alsab.boozycalc.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "parties")
public class PartyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name", unique = true)
    private String name;
    @Column(name = "event_date")
    private java.sql.Timestamp event_date;
    @Column(name = "location")
    private String location;
    @Column(name = "description")
    private String description;

    public PartyEntity() {
    }

    public PartyEntity(String name, Timestamp date, String location, String description) {
        this.name = name;
        this.event_date = date;
        this.location = location;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getDate() {
        return event_date;
    }

    public void setDate(Timestamp date) {
        this.event_date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}