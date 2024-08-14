package com.liquibaseproject.repository;

import com.liquibaseproject.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.query.Procedure;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public interface UsersRepository extends JpaRepository<Users, UUID>, JpaSpecificationExecutor<Users> {

    List<Users> findByUsernameIgnoreCase(String userName);

    @Procedure(name = "insertUser")
    void insertUser(String p_username, String p_password, String p_email, String o_username, Timestamp o_created_at, Timestamp o_updated_at);
}