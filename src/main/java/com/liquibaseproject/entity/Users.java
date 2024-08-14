package com.liquibaseproject.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "users")
@NamedStoredProcedureQuery(
        name = "insertUser",
        procedureName = "insert_user",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_username", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_password", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_email", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.OUT, name = "o_username", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.OUT, name = "o_created_at", type = Timestamp.class),
                @StoredProcedureParameter(mode = ParameterMode.OUT, name = "o_updated_at", type = Timestamp.class)
        }
)
public class Users {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id + '\'' +
                "username=" + username + '\'' +
                "password=" + password + '\'' +
                "email=" + email + '\'' +
                "createdAt=" + createdAt + '\'' +
                "updatedAt=" + updatedAt + '\'' +
                '}';
    }
}
