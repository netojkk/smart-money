package com.neto.smart_money.domain.enums;

//creating the roles
public enum ClientRole {

    ADMIN("admin"),
    CLIENT("client");

    private String role;

    ClientRole(String role){
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
