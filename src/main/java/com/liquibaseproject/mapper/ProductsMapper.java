package com.liquibaseproject.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductsMapper {

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    com.liquibaseproject.entity.Products toEntity(com.liquibaseproject.model.Products model);

    com.liquibaseproject.model.Products toModel(com.liquibaseproject.entity.Products entity);
}