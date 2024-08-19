package com.liquibaseproject.api;

import com.liquibaseproject.exception.EmptyInputException;
import com.liquibaseproject.exception.ResultsNotFoundException;
import com.liquibaseproject.mapper.NewProductsMapper;
import com.liquibaseproject.mapper.ProductsMapper;
import com.liquibaseproject.model.ApiResponseSchema;
import com.liquibaseproject.model.NewProduct;
import com.liquibaseproject.model.Products;
import com.liquibaseproject.service.ProductsService;
import io.micrometer.common.util.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductsApiDelegateImpl implements ProductsApiDelegate {

    private static final String EMPTY_INPUT_EXCEPTION_MESSAGE = "No products provided";
    private static final String PRODUCTS_NOT_FOUND_EXCEPTION_MESSAGE = "No products found for the provided input";
    private static final String SUCCESS_MESSAGE = "Product has been successfully %s";

    private final ProductsMapper productsMapper;
    private final NewProductsMapper newProductsMapper;
    private final ProductsService productsService;

    public ProductsApiDelegateImpl(ProductsMapper productsMapper, NewProductsMapper newProductsMapper, ProductsService productsService) {
        this.productsMapper = productsMapper;
        this.newProductsMapper = newProductsMapper;
        this.productsService = productsService;
    }

    @Override
    public ResponseEntity<Products> addProduct(NewProduct newProduct) {
        com.liquibaseproject.entity.Products entity = newProductsMapper.toEntity(newProduct);
        com.liquibaseproject.entity.Products createdProduct = productsService.addProduct(entity);
        return ResponseEntity.ok(productsMapper.toModel(createdProduct));
    }

    @Override
    public ResponseEntity<ApiResponseSchema> deleteProduct(UUID id, String apiKey) {
        productsService.deleteProduct(id);

        ApiResponseSchema response = getApiResponseSchema(String.format(SUCCESS_MESSAGE, "deleted"));

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<List<Products>> findAllProducts() {
        List<com.liquibaseproject.entity.Products> productsEntityList = productsService.findAll();
        List<Products> products = productsEntityList.stream()
                .map(productsMapper::toModel)
                .toList();

        return ResponseEntity.ok(products);
    }

    @Override
    public ResponseEntity<List<Products>> findProductsByName(String names) {
        if (StringUtils.isEmpty(names)) {
            throw new EmptyInputException(EMPTY_INPUT_EXCEPTION_MESSAGE);
        }

        String[] productsList = names.split(",");
        Set<String> uniqueProducts = new LinkedHashSet<>(Arrays.asList(productsList));
        List<com.liquibaseproject.entity.Products> productEntities = new ArrayList<>();

        for (String name : uniqueProducts) {
            productEntities.addAll(productsService.findByNameIgnoreCase(name.trim()));
        }

        if (productEntities.isEmpty()) {
            throw new ResultsNotFoundException(String.format(PRODUCTS_NOT_FOUND_EXCEPTION_MESSAGE));
        }

        List<Products> products = productEntities.stream()
                .map(productsMapper::toModel)
                .toList();
        return ResponseEntity.ok(products);
    }

    @Override
    public ResponseEntity<Products> getProductById(UUID id) {
        Optional<com.liquibaseproject.entity.Products> productsEntity = productsService.getProductById(id);
        return productsEntity.map(productsMapper::toModel).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<ApiResponseSchema> updateProduct(Products products) {
        productsService.updateProduct(products.getId(), productsMapper.toEntity(products));

        ApiResponseSchema response = getApiResponseSchema(String.format(SUCCESS_MESSAGE, "updated"));

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ApiResponseSchema> updateProductWithForm(UUID id, String name, String description, Double price, Integer quantity) {
        Optional<com.liquibaseproject.entity.Products> productsEntityOptional = productsService.getProductById(id);

        productsEntityOptional.ifPresent(productEntity -> {
            if (StringUtils.isNotEmpty(name)) {
                productEntity.setName(name);
            }
            if (StringUtils.isNotEmpty(description)) {
                productEntity.setDescription(description);
            }
            if (Objects.nonNull(price)) {
                productEntity.setPrice(price);
            }
            if (Objects.nonNull(quantity)) {
                productEntity.setQuantity(quantity);
            }

            productsService.updateProduct(id, productEntity);
        });

        ApiResponseSchema response = getApiResponseSchema(String.format(SUCCESS_MESSAGE, "updated"));

        return ResponseEntity.ok(response);
    }

    private static ApiResponseSchema getApiResponseSchema(String message) {
        ApiResponseSchema response = new ApiResponseSchema();
        response.setCode(200);
        response.setType("success");
        response.setMessage(message);
        return response;
    }
}