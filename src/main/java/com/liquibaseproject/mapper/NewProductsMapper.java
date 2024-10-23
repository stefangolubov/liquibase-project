package com.liquibaseproject.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NewProductsMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    com.liquibaseproject.entity.Products toEntity(com.liquibaseproject.model.NewProduct model);

    com.liquibaseproject.model.NewProduct toModel(com.liquibaseproject.entity.Products entity);
}