package com.lsc.software.api.Dto;

import jakarta.validation.constraints.*;

public class UserSingUp {

    private Long id;

    @NotNull
    @NotBlank
    @NotEmpty
    @Size(min = 2, max = 20)
    private String firstName;

    @NotNull
    @NotBlank
    @NotEmpty
    @Size(min = 2, max = 20)
    private String lastName;

    @NotNull
    @NotBlank
    @NotEmpty
    @Size(min = 2, max = 20)
    private String username;

    @NotNull
    @NotBlank
    @NotEmpty
    @Size(min = 2, max = 20)
    private String email;

    @Email
    @NotNull
    @NotBlank
    @NotEmpty
    @Size(min = 2, max = 20)
    private String password;

    private String roles = null;

    private boolean active = false;

    public UserSingUp() {

    }

    public UserSingUp(Long id, String firstName, String lastName, String email, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull @NotBlank @NotEmpty @Size(min = 2, max = 20) String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NotNull @NotBlank @NotEmpty @Size(min = 2, max = 20) String firstName) {
        this.firstName = firstName;
    }

    public @NotNull @NotBlank @NotEmpty @Size(min = 2, max = 20) String getLastName() {
        return lastName;
    }

    public void setLastName(@NotNull @NotBlank @NotEmpty @Size(min = 2, max = 20) String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public @NotNull @NotBlank @NotEmpty @Size(min = 2, max = 20) String getEmail() {
        return email;
    }

    public void setEmail(@NotNull @NotBlank @NotEmpty @Size(min = 2, max = 20) String email) {
        this.email = email;
    }

    public @Email @NotNull @NotBlank @NotEmpty @Size(min = 2, max = 20) String getPassword() {
        return password;
    }

    public void setPassword(@Email @NotNull @NotBlank @NotEmpty @Size(min = 2, max = 20) String password) {
        this.password = password;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
