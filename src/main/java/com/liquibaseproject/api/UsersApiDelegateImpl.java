package com.liquibaseproject.api;

import com.liquibaseproject.mapper.NewUserMapper;
import com.liquibaseproject.mapper.UsersMapper;
import com.liquibaseproject.model.ModelApiResponse;
import com.liquibaseproject.model.NewUser;
import com.liquibaseproject.model.Users;
import com.liquibaseproject.service.UsersService;
import io.micrometer.common.util.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsersApiDelegateImpl implements UsersApiDelegate {

    private final UsersService usersService;
    private final UsersMapper usersMapper;
    private final NewUserMapper newUserMapper;

    public UsersApiDelegateImpl(UsersService usersService, UsersMapper usersMapper, NewUserMapper newUserMapper) {
        this.usersService = usersService;
        this.usersMapper = usersMapper;
        this.newUserMapper = newUserMapper;
    }

    @Override
    public ResponseEntity<ModelApiResponse> updateUser(Users usersModel) {
        usersService.updateUser(usersModel.getId(), usersMapper.toEntity(usersModel));

        ModelApiResponse response = new ModelApiResponse();
        response.setCode(200);
        response.setType("success");
        response.setMessage("User has been successfully updated");

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Users> addUser(NewUser usersModel) {
        com.liquibaseproject.entity.Users entity = newUserMapper.toEntity(usersModel);
        com.liquibaseproject.entity.Users createdUser = usersService.addUser(entity);
        return ResponseEntity.ok(usersMapper.toModel(createdUser));
    }

    @Override
    public ResponseEntity<Void> deleteUser(UUID id, String apiKey) {
        usersService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<Users>> findUsersByUsername(String usernames) {
        if (StringUtils.isEmpty(usernames)) {
            return ResponseEntity.badRequest().build();
        }

        String[] usernameList = usernames.split(",");
        List<com.liquibaseproject.entity.Users> userEntities = new ArrayList<>();

        for (String username : usernameList) {
            userEntities.addAll(usersService.findByUsernameIgnoreCase(username.trim()));
        }

        if (userEntities.isEmpty()) {
            return ResponseEntity.notFound().build();
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
    public ResponseEntity<Void> updateUserWithForm(UUID id, String username, String email) {
        if (id == null || (username == null && email == null)) {
            return ResponseEntity.badRequest().build();
        }

        Optional<com.liquibaseproject.entity.Users> userEntityOptional = usersService.getUserById(id);
        if (userEntityOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        com.liquibaseproject.entity.Users userEntity = userEntityOptional.get();
        if (StringUtils.isNotEmpty(username)) {
            userEntity.setUsername(username);
        }
        if (StringUtils.isNotEmpty(email)) {
            userEntity.setEmail(email);
        }

        usersService.updateUser(id, userEntity);

        return ResponseEntity.noContent().build();
    }
}