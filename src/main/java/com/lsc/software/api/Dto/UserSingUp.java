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
    private String email;

    @Email
    @NotNull
    @NotBlank
    @NotEmpty
    @Size(min = 2, max = 20)
    private String password;

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

}
