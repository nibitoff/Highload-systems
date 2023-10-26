package com.alsab.boozycalc.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "invites")
public class InviteEntity {
    @EmbeddedId
    private InviteId id;
    public InviteEntity() {
    }

    public InviteId getId() {
        return id;
    }

    public void setId(InviteId id) {
        this.id = id;
    }
}