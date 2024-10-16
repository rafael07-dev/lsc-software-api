package com.lsc.software.api.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

@Entity
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    private Date createdAt;

    @OneToOne
    @JoinColumn(unique = true, nullable = false, name = "user_id")
    private UserEntity user;

    public ConfirmationToken() {}

    public ConfirmationToken(UserEntity user) {
        this.user = user;
        this.createdAt = new Date();
        this.token = UUID.randomUUID().toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
