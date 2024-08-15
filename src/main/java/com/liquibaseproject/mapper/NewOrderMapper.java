package com.liquibaseproject.mapper;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NewOrderMapper {
    com.liquibaseproject.entity.Orders toEntity(com.liquibaseproject.model.NewOrder model);

    com.liquibaseproject.model.NewOrder toModel(com.liquibaseproject.entity.Orders entity);
}