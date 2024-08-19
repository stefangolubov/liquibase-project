package com.liquibaseproject.service;

import com.liquibaseproject.entity.Products;
import com.liquibaseproject.exception.ResultsNotFoundException;
import com.liquibaseproject.repository.ProductsRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductsService {

    private final ProductsRepository productsRepository;

    private static final String PRODUCT_NOT_FOUND_MESSAGE = "Product with ID %s does not exist.";

    public ProductsService(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    public Optional<Products> getProductById(UUID id) {
        if (productsRepository.existsById(id)) {
            return productsRepository.findById(id);
        } else {
            throw new ResultsNotFoundException(String.format(PRODUCT_NOT_FOUND_MESSAGE, id));
        }
    }

    public List<Products> findByNameIgnoreCase(String name) {
        return productsRepository.findByNameIgnoreCase(name);
    }

    public List<Products> findAll() {
        return productsRepository.findAll();
    }

    public Products addProduct(Products productsEntity) {
        return productsRepository.save(productsEntity);
    }

    public void updateProduct(UUID id, Products productsEntity) {
        Products product = productsRepository.findById(id)
                .orElseThrow(() -> new ResultsNotFoundException(String.format(PRODUCT_NOT_FOUND_MESSAGE, id)));

        if (StringUtils.isNotEmpty(productsEntity.getName())) {
            product.setName(productsEntity.getName());
        }

        if (StringUtils.isNotEmpty(productsEntity.getDescription())) {
            product.setDescription(productsEntity.getDescription());
        }

        if (Objects.nonNull(productsEntity.getPrice())) {
            product.setPrice(productsEntity.getPrice());
        }

        if (Objects.nonNull(productsEntity.getQuantity())) {
            product.setQuantity(productsEntity.getQuantity());
        }

        productsRepository.save(product);
    }

    public void deleteProduct(UUID id) {
        if (productsRepository.existsById(id)) {
            productsRepository.deleteById(id);
        } else {
            throw new ResultsNotFoundException(String.format(PRODUCT_NOT_FOUND_MESSAGE, id));
        }
    }
}