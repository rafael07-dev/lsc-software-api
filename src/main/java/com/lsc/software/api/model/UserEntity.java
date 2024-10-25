package com.lsc.software.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.Date;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @NotNull
    @NotBlank
    @NotEmpty
    @Size(min = 2, max = 30)
    private String firstName;

    @NotNull
    @NotBlank
    @NotEmpty
    @Size(min = 2, max = 30)
    private String lastName;

    @NotNull
    @NotBlank
    @NotEmpty
    @Size(min = 2, max = 20)
    private String username;

    @Email
    @NotNull
    @NotBlank
    @NotEmpty
    @Size(min = 2, max = 40)
    @Column(unique = true, nullable = false)
    private String email;

    @NotNull
    @NotBlank
    @NotEmpty
    private String password;

    @NotEmpty
    @NotBlank
    @NotNull
    private String roles;

    private Date created_at;

    private boolean active = false;

    public UserEntity() {
        this.created_at = new Date();
    }

    public UserEntity(Long id, String firstName, String lastName, String username, String email, String password, String roles, Date created_at) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.created_at = created_at;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull @NotBlank @NotEmpty @Size(min = 2, max = 30) String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NotNull @NotBlank @NotEmpty @Size(min = 2, max = 30) String firstName) {
        this.firstName = firstName;
    }

    public @NotNull @NotBlank @NotEmpty @Size(min = 2, max = 30) String getLastName() {
        return lastName;
    }

    public void setLastName(@NotNull @NotBlank @NotEmpty @Size(min = 2, max = 30) String lastName) {
        this.lastName = lastName;
    }

    public @NotNull @NotBlank @NotEmpty @Size(min = 2, max = 20) String getUsername() {
        return username;
    }

    public void setUsername(@NotNull @NotBlank @NotEmpty @Size(min = 2, max = 20) String username) {
        this.username = username;
    }

    public @NotNull @NotBlank @NotEmpty @Size(min = 2, max = 40) String getEmail() {
        return email;
    }

    public void setEmail(@NotNull @NotBlank @NotEmpty @Size(min = 2, max = 40) String email) {
        this.email = email;
    }

    public @NotNull @NotBlank @NotEmpty String getPassword() {
        return password;
    }

    public void setPassword(@NotNull @NotBlank @NotEmpty String password) {
        this.password = password;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
