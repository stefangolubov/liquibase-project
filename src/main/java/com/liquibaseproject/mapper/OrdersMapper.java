package com.liquibaseproject.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrdersMapper {

    @Mapping(target = "orderDate", ignore = true)
    com.liquibaseproject.entity.Orders toEntity(com.liquibaseproject.model.Orders model);

    com.liquibaseproject.model.Orders toModel(com.liquibaseproject.entity.Orders entity);
}