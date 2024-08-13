package com.liquibaseproject.mapper;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NewUserMapper {
    com.liquibaseproject.entity.Users toEntity(com.liquibaseproject.model.NewUser model);

    com.liquibaseproject.model.NewUser toModel(com.liquibaseproject.entity.Users entity);
}