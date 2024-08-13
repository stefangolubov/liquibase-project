package com.liquibaseproject.repository;

import com.liquibaseproject.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

public interface ProductsRepository extends JpaRepository<Products, UUID>, JpaSpecificationExecutor<Products> {

    List<Products> findByNameIgnoreCase(String name);
}