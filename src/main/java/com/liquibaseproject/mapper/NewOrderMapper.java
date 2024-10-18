package com.liquibaseproject.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NewOrderMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderDate", ignore = true)
    @Mapping(target = "status", ignore = true)
    com.liquibaseproject.entity.Orders toEntity(com.liquibaseproject.model.NewOrder model);

    com.liquibaseproject.model.NewOrder toModel(com.liquibaseproject.entity.Orders entity);
}