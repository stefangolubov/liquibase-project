package com.liquibaseproject.unit.service;

import com.liquibaseproject.entity.Products;
import com.liquibaseproject.exception.ServiceProcessingException;
import com.liquibaseproject.repository.ProductsRepository;
import com.liquibaseproject.service.ProductsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.liquibaseproject.constant.ExceptionConstants.PRODUCTS_NOT_FOUND_EXCEPTION_MESSAGE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductsServiceTest {

    @Mock
    private ProductsRepository productsRepository;

    @InjectMocks
    private ProductsService productsService;

    private Products product;
    private UUID productId;

    @BeforeEach
    void setUp() {
        productId = UUID.randomUUID();
        product = new Products();
        product.setId(productId);
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(100.0);
        product.setQuantity(10);
    }

    @Test
    void testGetProductById_ProductExists() {
        when(productsRepository.existsById(productId)).thenReturn(true);
        when(productsRepository.findById(productId)).thenReturn(Optional.of(product));

        Optional<Products> result = productsService.getProductById(productId);

        assertTrue(result.isPresent());
        assertEquals(product, result.get());
    }

    @Test
    void testGetProductById_ProductDoesNotExist() {
        when(productsRepository.existsById(productId)).thenReturn(false);

        ServiceProcessingException exception = assertThrows(ServiceProcessingException.class, () -> productsService.getProductById(productId));

        assertEquals(PRODUCTS_NOT_FOUND_EXCEPTION_MESSAGE, exception.getMessage());
    }

    @Test
    void testFindByNameIgnoreCase() {
        when(productsRepository.findByNameIgnoreCase("Test Product")).thenReturn(Collections.singletonList(product));

        List<Products> result = productsService.findByNameIgnoreCase("Test Product");

        assertEquals(1, result.size());
        assertEquals(product, result.getFirst());
    }

    @Test
    void testFindAll() {
        when(productsRepository.findAll()).thenReturn(Collections.singletonList(product));

        List<Products> result = productsService.findAll();

        assertEquals(1, result.size());
        assertEquals(product, result.getFirst());
    }

    @Test
    void testAddProduct() {
        when(productsRepository.save(product)).thenReturn(product);

        Products result = productsService.addProduct(product);

        assertEquals(product, result);
    }

    @Test
    void testUpdateProduct_ProductExists() {
        Products updatedProduct = new Products();
        updatedProduct.setName("Updated Product");
        updatedProduct.setDescription("Updated Description");
        updatedProduct.setPrice(150.0);
        updatedProduct.setQuantity(20);

        when(productsRepository.findById(productId)).thenReturn(Optional.of(product));

        productsService.updateProduct(productId, updatedProduct);

        verify(productsRepository).save(product);
        assertEquals("Updated Product", product.getName());
        assertEquals("Updated Description", product.getDescription());
        assertEquals(150.0, product.getPrice());
        assertEquals(20, product.getQuantity());
    }

    @Test
    void testUpdateProduct_ProductDoesNotExist() {
        Products updatedProduct = new Products();
        updatedProduct.setName("Updated Product");
        updatedProduct.setDescription("Updated Description");
        updatedProduct.setPrice(150.0);
        updatedProduct.setQuantity(20);

        when(productsRepository.findById(productId)).thenReturn(Optional.empty());

        ServiceProcessingException exception = assertThrows(ServiceProcessingException.class, () -> productsService.updateProduct(productId, updatedProduct));

        assertEquals(PRODUCTS_NOT_FOUND_EXCEPTION_MESSAGE, exception.getMessage());
    }

    @Test
    void testDeleteProduct_ProductExists() {
        when(productsRepository.existsById(productId)).thenReturn(true);

        productsService.deleteProduct(productId);

        verify(productsRepository).deleteById(productId);
    }

    @Test
    void testDeleteProduct_ProductDoesNotExist() {
        when(productsRepository.existsById(productId)).thenReturn(false);

        ServiceProcessingException exception = assertThrows(ServiceProcessingException.class, () -> productsService.deleteProduct(productId));

        assertEquals(PRODUCTS_NOT_FOUND_EXCEPTION_MESSAGE, exception.getMessage());
    }
}