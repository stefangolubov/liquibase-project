package com.liquibaseproject.mapper;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NewProductsMapper {
    com.liquibaseproject.entity.Products toEntity(com.liquibaseproject.model.NewProduct model);

    com.liquibaseproject.model.NewProduct toModel(com.liquibaseproject.entity.Products entity);
}