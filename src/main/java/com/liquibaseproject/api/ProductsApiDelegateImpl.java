package com.liquibaseproject.api;

import com.liquibaseproject.mapper.NewProductsMapper;
import com.liquibaseproject.mapper.ProductsMapper;
import com.liquibaseproject.model.ModelApiResponse;
import com.liquibaseproject.model.NewProduct;
import com.liquibaseproject.model.Products;
import com.liquibaseproject.service.ProductsService;
import io.micrometer.common.util.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductsApiDelegateImpl implements ProductsApiDelegate {

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
    public ResponseEntity<Void> deleteProduct(UUID id, String apiKey) {
        productsService.deleteProduct(id);
        return ResponseEntity.noContent().build();
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
            return ResponseEntity.badRequest().build();
        }

        String[] productsList = names.split(",");
        List<com.liquibaseproject.entity.Products> productEntities = new ArrayList<>();

        for (String name : productsList) {
            productEntities.addAll(productsService.findByNameIgnoreCase(name.trim()));
        }

        if (productEntities.isEmpty()) {
            return ResponseEntity.notFound().build();
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
    public ResponseEntity<ModelApiResponse> updateProduct(Products products) {
        productsService.updateProduct(products.getId(), productsMapper.toEntity(products));

        ModelApiResponse response = new ModelApiResponse();
        response.setCode(200);
        response.setType("success");
        response.setMessage("Product has been successfully updated");

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Void> updateProductWithForm(UUID id, String name, String description, Double price, Integer quantity) {
        if (id == null || (name == null && description == null && price == null && quantity == null)) {
            return ResponseEntity.badRequest().build();
        }

        Optional<com.liquibaseproject.entity.Products> productsEntityOptional = productsService.getProductById(id);
        if (productsEntityOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        com.liquibaseproject.entity.Products productsEntity = productsEntityOptional.get();
        if (StringUtils.isNotEmpty(name)) {
            productsEntity.setName(name);
        }
        if (StringUtils.isNotEmpty(description)) {
            productsEntity.setDescription(description);
        }
        if (Objects.nonNull(price)) {
            productsEntity.setPrice(price);
        }
        if (Objects.nonNull(quantity)) {
            productsEntity.setQuantity(quantity);
        }

        productsService.updateProduct(id, productsEntity);

        return ResponseEntity.noContent().build();
    }
}