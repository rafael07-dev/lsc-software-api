package com.lsc.software.api.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UserAuth {

    @NotBlank
    @NotNull
    private String email;

    @NotBlank
    @NotNull
    private String password;

    public @NotBlank @NotNull String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank @NotNull String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
