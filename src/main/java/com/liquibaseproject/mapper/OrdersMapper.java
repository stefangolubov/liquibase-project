package com.liquibaseproject.mapper;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrdersMapper {

    com.liquibaseproject.entity.Orders toEntity(com.liquibaseproject.model.Orders model);

    com.liquibaseproject.model.Orders toModel(com.liquibaseproject.entity.Orders entity);
}