package com.bxl.demosecurity.dal.enums;

public enum RoleEnum {
    ADMIN("ADMIN"),
    USER("USER");

    final String value;

    RoleEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
