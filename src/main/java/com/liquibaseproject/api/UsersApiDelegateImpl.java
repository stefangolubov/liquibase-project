package com.liquibaseproject.api;

import com.liquibaseproject.exception.EmptyInputException;
import com.liquibaseproject.exception.ResultsNotFoundException;
import com.liquibaseproject.mapper.NewUserMapper;
import com.liquibaseproject.mapper.UsersMapper;
import com.liquibaseproject.model.ApiResponseSchema;
import com.liquibaseproject.model.NewUser;
import com.liquibaseproject.model.Users;
import com.liquibaseproject.service.UsersService;
import io.micrometer.common.util.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UsersApiDelegateImpl implements UsersApiDelegate {

    private static final String EMPTY_INPUT_EXCEPTION_MESSAGE = "No usernames provided";
    private static final String USERS_NOT_FOUND_EXCEPTION_MESSAGE = "No users found for the provided input";
    private static final String SUCCESS_MESSAGE = "User has been successfully %s";

    private final UsersService usersService;
    private final UsersMapper usersMapper;
    private final NewUserMapper newUserMapper;

    public UsersApiDelegateImpl(UsersService usersService, UsersMapper usersMapper, NewUserMapper newUserMapper) {
        this.usersService = usersService;
        this.usersMapper = usersMapper;
        this.newUserMapper = newUserMapper;
    }

    @Override
    public ResponseEntity<ApiResponseSchema> updateUser(Users usersModel) {
        usersService.updateUser(usersModel.getId(), usersMapper.toEntity(usersModel));

        ApiResponseSchema response = getApiResponseSchema(String.format(SUCCESS_MESSAGE, "updated"));

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Users> addUser(NewUser usersModel) {
        com.liquibaseproject.entity.Users entity = newUserMapper.toEntity(usersModel);
        com.liquibaseproject.entity.Users createdUser = usersService.addUser(entity);
        return ResponseEntity.ok(usersMapper.toModel(createdUser));
    }

    @Override
    public ResponseEntity<ApiResponseSchema> deleteUser(UUID id, String apiKey) {
        usersService.deleteUser(id);

        ApiResponseSchema response = getApiResponseSchema(String.format(SUCCESS_MESSAGE, "deleted"));

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<List<Users>> findUsersByUsername(String usernames) {
        if (StringUtils.isEmpty(usernames)) {
            throw new EmptyInputException(EMPTY_INPUT_EXCEPTION_MESSAGE);
        }

        String[] usernameList = usernames.split(",");
        Set<String> uniqueUsers = new LinkedHashSet<>(Arrays.asList(usernameList));
        List<com.liquibaseproject.entity.Users> userEntities = new ArrayList<>();

        for (String username : uniqueUsers) {
            userEntities.addAll(usersService.findByUsernameIgnoreCase(username.trim()));
        }

        if (userEntities.isEmpty()) {
            throw new ResultsNotFoundException(String.format(USERS_NOT_FOUND_EXCEPTION_MESSAGE));
        }

        List<Users> users = userEntities.stream()
                .map(usersMapper::toModel)
                .toList();
        return ResponseEntity.ok(users);
    }

    @Override
    public ResponseEntity<List<Users>> findAllUsers() {
        List<com.liquibaseproject.entity.Users> usersEntityList = usersService.findAll();
        List<Users> users = usersEntityList.stream()
                .map(usersMapper::toModel)
                .toList();

        return ResponseEntity.ok(users);
    }

    @Override
    public ResponseEntity<Users> getUserById(UUID id) {
        Optional<com.liquibaseproject.entity.Users> userEntity = usersService.getUserById(id);
        return userEntity.map(usersMapper::toModel).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<ApiResponseSchema> updateUserWithForm(UUID id, String username, String email) {
        Optional<com.liquibaseproject.entity.Users> userEntityOptional = usersService.getUserById(id);

        userEntityOptional.ifPresent(userEntity -> {
            if (StringUtils.isNotEmpty(username)) {
                userEntity.setUsername(username);
            }
            if (StringUtils.isNotEmpty(email)) {
                userEntity.setEmail(email);
            }
            usersService.updateUser(id, userEntity);
        });

        ApiResponseSchema response = getApiResponseSchema(String.format(SUCCESS_MESSAGE, "updated"));

        return ResponseEntity.ok(response);
    }

    private static ApiResponseSchema getApiResponseSchema(String message) {
        ApiResponseSchema response = new ApiResponseSchema();

        response.setCode(200);
        response.setType("success");
        response.setMessage(message);
        return response;
    }
}