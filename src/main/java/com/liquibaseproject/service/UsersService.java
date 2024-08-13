package com.liquibaseproject.service;

import com.liquibaseproject.entity.Users;
import com.liquibaseproject.repository.UsersRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Service;

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

    public Users addUser(Users userEntity) {
        return usersRepository.save(userEntity);
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