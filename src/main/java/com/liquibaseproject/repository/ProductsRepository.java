package com.liquibaseproject.repository;

import com.liquibaseproject.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductsRepository extends JpaRepository<Products, String>, JpaSpecificationExecutor<Products> {

}