package com.liquibaseproject.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UsersMapper {
    UsersMapper INSTANCE = Mappers.getMapper(UsersMapper.class);

    com.liquibaseproject.entity.Users toEntity(com.liquibaseproject.model.Users model);

    com.liquibaseproject.model.Users toModel(com.liquibaseproject.entity.Users entity);
}