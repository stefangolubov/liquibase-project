package com.liquibaseproject.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@Data
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
}
