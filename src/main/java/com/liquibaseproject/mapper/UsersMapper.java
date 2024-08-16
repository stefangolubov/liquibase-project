package com.liquibaseproject.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsersMapper {

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    com.liquibaseproject.entity.Users toEntity(com.liquibaseproject.model.Users model);

    com.liquibaseproject.model.Users toModel(com.liquibaseproject.entity.Users entity);
}