package com.liquibaseproject.unit.service;

import com.liquibaseproject.entity.Users;
import com.liquibaseproject.exception.ServiceProcessingException;
import com.liquibaseproject.repository.UsersRepository;
import com.liquibaseproject.service.UsersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsersServiceTest {

    @Mock
    private UsersRepository usersRepository;

    @InjectMocks
    private UsersService usersService;

    private Users user;
    private UUID userId;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        user = new Users();
        user.setId(userId);
        user.setUsername("testuser");
        user.setPassword("password");
        user.setEmail("testuser@example.com");
        user.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        user.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
    }

    @Test
    void testGetUserById_UserExists() {
        when(usersRepository.existsById(userId)).thenReturn(true);
        when(usersRepository.findById(userId)).thenReturn(Optional.of(user));

        Optional<Users> result = usersService.getUserById(userId);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    void testGetUserById_UserDoesNotExist() {
        when(usersRepository.existsById(userId)).thenReturn(false);

        ServiceProcessingException exception = assertThrows(ServiceProcessingException.class, () -> usersService.getUserById(userId));

        assertEquals("No users found for the provided input", exception.getMessage());
    }

    @Test
    void testFindByUsernameIgnoreCase() {
        when(usersRepository.findByUsernameIgnoreCase("testuser")).thenReturn(Collections.singletonList(user));

        List<Users> result = usersService.findByUsernameIgnoreCase("testuser");

        assertEquals(1, result.size());
        assertEquals(user, result.getFirst());
    }

    @Test
    void testFindAll() {
        when(usersRepository.findAll()).thenReturn(Collections.singletonList(user));

        List<Users> result = usersService.findAll();

        assertEquals(1, result.size());
        assertEquals(user, result.getFirst());
    }

    @Test
    void testAddUser() {
        UUID generatedId = UUID.randomUUID();
        Users createdUser = new Users();
        createdUser.setId(generatedId);
        createdUser.setUsername(user.getUsername());
        createdUser.setPassword(user.getPassword());
        createdUser.setEmail(user.getEmail());
        createdUser.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        createdUser.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        when(usersRepository.insertUser(anyString(), anyString(), anyString(), any(UUID.class), any(Timestamp.class), any(Timestamp.class)))
                .thenReturn(generatedId);
        when(usersRepository.findById(generatedId)).thenReturn(Optional.of(createdUser));

        Users result = usersService.addUser(user);

        verify(usersRepository).insertUser(
                eq(user.getUsername()),
                eq(user.getPassword()),
                eq(user.getEmail()),
                any(UUID.class),
                any(Timestamp.class),
                any(Timestamp.class)
        );

        assertNotNull(result.getId());
        assertEquals(generatedId, result.getId());
        assertEquals(createdUser.getCreatedAt(), result.getCreatedAt());
        assertEquals(createdUser.getUpdatedAt(), result.getUpdatedAt());
    }

    @Test
    void testUpdateUser_UserExists() {
        Users updatedUser = new Users();
        updatedUser.setUsername("updateduser");
        updatedUser.setEmail("updateduser@example.com");

        when(usersRepository.findById(userId)).thenReturn(Optional.of(user));

        usersService.updateUser(userId, updatedUser);

        verify(usersRepository).save(user);
        assertEquals("updateduser", user.getUsername());
        assertEquals("updateduser@example.com", user.getEmail());
    }

    @Test
    void testUpdateUser_UserDoesNotExist() {
        Users updatedUser = new Users();
        updatedUser.setUsername("updateduser");
        updatedUser.setEmail("updateduser@example.com");

        when(usersRepository.findById(userId)).thenReturn(Optional.empty());

        ServiceProcessingException exception = assertThrows(ServiceProcessingException.class, () -> usersService.updateUser(userId, updatedUser));

        assertEquals("No users found for the provided input", exception.getMessage());
    }

    @Test
    void testDeleteUser_UserExists() {
        when(usersRepository.existsById(userId)).thenReturn(true);

        usersService.deleteUser(userId);

        verify(usersRepository).deleteById(userId);
    }

    @Test
    void testDeleteUser_UserDoesNotExist() {
        when(usersRepository.existsById(userId)).thenReturn(false);

        ServiceProcessingException exception = assertThrows(ServiceProcessingException.class, () -> usersService.deleteUser(userId));

        assertEquals("No users found for the provided input", exception.getMessage());
    }
}