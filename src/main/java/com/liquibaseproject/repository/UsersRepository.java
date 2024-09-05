package com.liquibaseproject.repository;

import com.liquibaseproject.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public interface UsersRepository extends JpaRepository<Users, UUID>, JpaSpecificationExecutor<Users> {

    List<Users> findByUsernameIgnoreCase(String userName);

    @Query(value = "CALL insert_user(:username, :password, :email, :id, :createdAt, :updatedAt)", nativeQuery = true)
    UUID insertUser(@Param("username") String username,
                    @Param("password") String password,
                    @Param("email") String email,
                    @Param("id") UUID id,
                    @Param("createdAt") Timestamp createdAt,
                    @Param("updatedAt") Timestamp updatedAt);
}
