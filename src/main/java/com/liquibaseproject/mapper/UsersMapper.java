package com.liquibaseproject.mapper;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsersMapper {
    com.liquibaseproject.entity.Users toEntity(com.liquibaseproject.model.Users model);

    com.liquibaseproject.model.Users toModel(com.liquibaseproject.entity.Users entity);
}