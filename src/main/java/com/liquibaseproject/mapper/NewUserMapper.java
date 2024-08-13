package com.liquibaseproject.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface NewUserMapper {
    NewUserMapper INSTANCE = Mappers.getMapper(NewUserMapper.class);

    com.liquibaseproject.entity.Users toEntity(com.liquibaseproject.model.NewUser model);

    com.liquibaseproject.model.NewUser toModel(com.liquibaseproject.entity.Users entity);
}