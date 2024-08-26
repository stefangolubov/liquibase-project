package com.liquibaseproject.api;

import com.liquibaseproject.model.ApiResponseSchema;
import com.liquibaseproject.model.NewProduct;
import com.liquibaseproject.model.Products;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import jakarta.annotation.Generated;

/**
 * A delegate to be called by the {@link ProductsPathApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.7.0")
public interface ProductsPathApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * POST /${products.path} : Add a new product
     * Add a new product
     *
     * @param newProduct Create a new product (required)
     * @return Product has been successfully added (status code 200)
     *         or Access forbidden (status code 403)
     * @see ProductsPathApi#addProduct
     */
    default ResponseEntity<Products> addProduct(NewProduct newProduct) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"quantity\" : 85, \"price\" : \"850.5\", \"name\" : \"Smart TV\", \"description\" : \"Samsung Smart TV\", \"id\" : \"6574214f-89e3-4001-b54d-0d185163f7a2\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/xml"))) {
                    String exampleString = "<product> <id>6574214f-89e3-4001-b54d-0d185163f7a2</id> <name>Smart TV</name> <description>Samsung Smart TV</description> <price>850.5</price> <quantity>85</quantity> </product>";
                    ApiUtil.setExampleResponse(request, "application/xml", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"code\" : 0, \"type\" : \"type\", \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * DELETE /${products.path}/{id} : Deletes a product
     * delete a product
     *
     * @param id Product ID for the product that needs to be deleted (required)
     * @return Product found by ID (status code 200)
     * @see ProductsPathApi#deleteProduct
     */
    default ResponseEntity<ApiResponseSchema> deleteProduct(UUID id) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"code\" : 0, \"type\" : \"type\", \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/xml"))) {
                    String exampleString = "<##default> <code>123</code> <type>aeiou</type> <message>aeiou</message> </##default>";
                    ApiUtil.setExampleResponse(request, "application/xml", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /${products.path}/findAll : List all products
     *
     * @return Products have been successfully listed (status code 200)
     * @see ProductsPathApi#findAllProducts
     */
    default ResponseEntity<List<Products>> findAllProducts() {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "[ { \"quantity\" : 85, \"price\" : \"850.5\", \"name\" : \"Smart TV\", \"description\" : \"Samsung Smart TV\", \"id\" : \"6574214f-89e3-4001-b54d-0d185163f7a2\" }, { \"quantity\" : 85, \"price\" : \"850.5\", \"name\" : \"Smart TV\", \"description\" : \"Samsung Smart TV\", \"id\" : \"6574214f-89e3-4001-b54d-0d185163f7a2\" } ]";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/xml"))) {
                    String exampleString = "<product> <id>6574214f-89e3-4001-b54d-0d185163f7a2</id> <name>Smart TV</name> <description>Samsung Smart TV</description> <price>850.5</price> <quantity>85</quantity> </product>";
                    ApiUtil.setExampleResponse(request, "application/xml", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /${products.path}/findByName : Find products by product name
     * Multiple products can be provided with comma separated strings (case insensitive)
     *
     * @param names  (optional)
     * @return Products have been successfully found by product name (status code 200)
     * @see ProductsPathApi#findProductsByName
     */
    default ResponseEntity<List<Products>> findProductsByName(String names) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "[ { \"quantity\" : 85, \"price\" : \"850.5\", \"name\" : \"Smart TV\", \"description\" : \"Samsung Smart TV\", \"id\" : \"6574214f-89e3-4001-b54d-0d185163f7a2\" }, { \"quantity\" : 85, \"price\" : \"850.5\", \"name\" : \"Smart TV\", \"description\" : \"Samsung Smart TV\", \"id\" : \"6574214f-89e3-4001-b54d-0d185163f7a2\" } ]";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/xml"))) {
                    String exampleString = "<product> <id>6574214f-89e3-4001-b54d-0d185163f7a2</id> <name>Smart TV</name> <description>Samsung Smart TV</description> <price>850.5</price> <quantity>85</quantity> </product>";
                    ApiUtil.setExampleResponse(request, "application/xml", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /${products.path}/{id} : Find product by ID
     * Returns a single product
     *
     * @param id Product ID to return (required)
     * @return Product found by ID (status code 200)
     * @see ProductsPathApi#getProductById
     */
    default ResponseEntity<Products> getProductById(UUID id) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"quantity\" : 85, \"price\" : \"850.5\", \"name\" : \"Smart TV\", \"description\" : \"Samsung Smart TV\", \"id\" : \"6574214f-89e3-4001-b54d-0d185163f7a2\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/xml"))) {
                    String exampleString = "<product> <id>6574214f-89e3-4001-b54d-0d185163f7a2</id> <name>Smart TV</name> <description>Samsung Smart TV</description> <price>850.5</price> <quantity>85</quantity> </product>";
                    ApiUtil.setExampleResponse(request, "application/xml", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * PUT /${products.path} : Update an existing product
     * Update an existing product by Id
     *
     * @param products Update an existing product (required)
     * @return Product has been successfully updated (status code 200)
     *         or Access forbidden (status code 403)
     * @see ProductsPathApi#updateProduct
     */
    default ResponseEntity<ApiResponseSchema> updateProduct(Products products) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"code\" : 0, \"type\" : \"type\", \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/xml"))) {
                    String exampleString = "<##default> <code>123</code> <type>aeiou</type> <message>aeiou</message> </##default>";
                    ApiUtil.setExampleResponse(request, "application/xml", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"code\" : 0, \"type\" : \"type\", \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * POST /${products.path}/{id} : Updates a product with form data
     * 
     *
     * @param id ID of product that needs to be updated (required)
     * @param name Name for the product that needs to be updated (optional)
     * @param description Description for the product that needs to be updated (optional)
     * @param price Price for the product that needs to be updated (optional)
     * @param quantity Quantity of the product that needs to be updated (optional)
     * @return Product found by ID (status code 200)
     *         or Access forbidden (status code 403)
     * @see ProductsPathApi#updateProductWithForm
     */
    default ResponseEntity<ApiResponseSchema> updateProductWithForm(UUID id,
        String name,
        String description,
        Double price,
        Integer quantity) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"code\" : 0, \"type\" : \"type\", \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/xml"))) {
                    String exampleString = "<##default> <code>123</code> <type>aeiou</type> <message>aeiou</message> </##default>";
                    ApiUtil.setExampleResponse(request, "application/xml", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"code\" : 0, \"type\" : \"type\", \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
