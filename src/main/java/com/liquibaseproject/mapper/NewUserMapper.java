package com.liquibaseproject.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NewUserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    com.liquibaseproject.entity.Users toEntity(com.liquibaseproject.model.NewUser model);

    com.liquibaseproject.model.NewUser toModel(com.liquibaseproject.entity.Users entity);
}