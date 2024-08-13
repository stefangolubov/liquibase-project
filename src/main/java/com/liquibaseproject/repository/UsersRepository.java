package com.liquibaseproject.repository;

import com.liquibaseproject.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

public interface UsersRepository extends JpaRepository<Users, UUID>, JpaSpecificationExecutor<Users> {

    List<Users> findByUsernameIgnoreCase(String userName);
}