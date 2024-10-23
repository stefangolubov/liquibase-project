package com.liquibaseproject.service;

import com.liquibaseproject.entity.Users;
import com.liquibaseproject.exception.ExceptionUtil;
import com.liquibaseproject.exception.LiquibaseProjectException;
import com.liquibaseproject.repository.UsersRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsersService {

    private final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public Optional<Users> getUserById(UUID id) {
        if (usersRepository.existsById(id)) {
            return usersRepository.findById(id);
        } else {
            throw ExceptionUtil.logAndBuildException(LiquibaseProjectException.USERS_NOT_FOUND_EXCEPTION);
        }
    }

    public List<Users> findByUsernameIgnoreCase(String userName) {
        return usersRepository.findByUsernameIgnoreCase(userName);
    }

    public List<Users> findAll() {
        return usersRepository.findAll();
    }

    public Users addUser(Users user) {
        UUID generatedId = usersRepository.insertUser(user.getUsername(), user.getPassword(), user.getEmail(), user.getId(),
                (Timestamp) user.getCreatedAt(), (Timestamp) user.getUpdatedAt());
        Users createdUser = usersRepository.findById(generatedId).orElseThrow();

        user.setId(generatedId);
        user.setCreatedAt(createdUser.getCreatedAt());
        user.setUpdatedAt(createdUser.getUpdatedAt());

        return user;
    }

    public void updateUser(UUID id, Users userEntity) {
        Users user = usersRepository.findById(id)
                .orElseThrow(() -> ExceptionUtil.logAndBuildException(LiquibaseProjectException.USERS_NOT_FOUND_EXCEPTION));

        if (StringUtils.isNotEmpty(userEntity.getUsername())) {
            user.setUsername(userEntity.getUsername());
        }

        if (StringUtils.isNotEmpty(userEntity.getEmail())) {
            user.setEmail(userEntity.getEmail());
        }

        usersRepository.save(user);
    }

    public void deleteUser(UUID id) {
        if (usersRepository.existsById(id)) {
            usersRepository.deleteById(id);
        } else {
            throw ExceptionUtil.logAndBuildException(LiquibaseProjectException.USERS_NOT_FOUND_EXCEPTION);
        }
    }
}