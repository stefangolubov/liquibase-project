package com.liquibaseproject.service;

import com.liquibaseproject.entity.Users;
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
        return usersRepository.findById(id);
    }

    public List<Users> findByUsernameIgnoreCase(String userName) {
        return usersRepository.findByUsernameIgnoreCase(userName);
    }

    public List<Users> findAll() {
        return usersRepository.findAll();
    }

    public Users addUser(Users user) {
        Timestamp createdAt = new Timestamp(System.currentTimeMillis());
        Timestamp updatedAt = new Timestamp(System.currentTimeMillis());

        usersRepository.insertUser(user.getUsername(), user.getPassword(), user.getEmail(), user.getUsername(), createdAt, updatedAt);

        UUID id = UUID.randomUUID();
        user.setId(id);
        user.setCreatedAt(createdAt);
        user.setUpdatedAt(updatedAt);

        return user;
    }

    public void updateUser(UUID id, Users userEntity) {
        Users user = usersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (StringUtils.isNotEmpty(userEntity.getUsername())) {
            user.setUsername(userEntity.getUsername());
        }
        if (StringUtils.isNotEmpty(userEntity.getEmail())) {
            user.setEmail(userEntity.getEmail());
        }

        usersRepository.save(user);
    }

    public void deleteUser(UUID id) {
        usersRepository.deleteById(id);
    }
}