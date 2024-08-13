package com.liquibaseproject.mapper;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductsMapper {

    com.liquibaseproject.entity.Products toEntity(com.liquibaseproject.model.Products model);

    com.liquibaseproject.model.Products toModel(com.liquibaseproject.entity.Products entity);
}