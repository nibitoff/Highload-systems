package com.alsab.boozycalc.entity;

import jakarta.persistence.EmbeddedId;

public class UserRoleEntity {
    @EmbeddedId
    private UserRoleId id;

    public UserRoleEntity(UserRoleId id) {
        this.id = id;
    }

    public UserRoleId getId() {
        return id;
    }

    public void setId(UserRoleId id) {
        this.id = id;
    }
}
