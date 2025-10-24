package br.com.fintech.API.user.model.enums;

public enum UserRole {
    AUTHORIZED("authorized"),
    USER("user");

    private String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
