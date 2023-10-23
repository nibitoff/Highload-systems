package com.alsab.boozycalc.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class RoleEntity {
    @EmbeddedId
    private RoleId id;
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    public RoleId getId() {
        return id;
    }

    public void setId(RoleId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
