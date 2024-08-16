package com.liquibaseproject.api;

import com.liquibaseproject.model.ApiResponseSchema;
import com.liquibaseproject.model.NewOrder;
import com.liquibaseproject.model.Orders;
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
 * A delegate to be called by the {@link OrdersApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.7.0")
public interface OrdersApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * POST /orders : Add a new order
     * Add a new order
     *
     * @param newOrder Place a new order (required)
     * @return Order has been successfully placed (status code 200)
     *         or Invalid input (status code 400)
     *         or Validation exception (status code 422)
     * @see OrdersApi#addOrder
     */
    default ResponseEntity<Orders> addOrder(NewOrder newOrder) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"quantity\" : 85, \"user_id\" : \"2be3e28c-53fb-482a-8cfd-85e168e99bed\", \"product_id\" : \"a320b172-4394-4b9a-8eb3-2fd999fdc460\", \"id\" : \"6574214f-89e3-4001-b54d-0d185163f7a2\", \"status\" : \"Shipped\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/xml"))) {
                    String exampleString = "<order> <id>6574214f-89e3-4001-b54d-0d185163f7a2</id> <user_id>2be3e28c-53fb-482a-8cfd-85e168e99bed</user_id> <product_id>a320b172-4394-4b9a-8eb3-2fd999fdc460</product_id> <quantity>85</quantity> <status>Shipped</status> </order>";
                    ApiUtil.setExampleResponse(request, "application/xml", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * DELETE /orders/{id} : Deletes an order
     * delete an order
     *
     * @param id ID of the order that needs to be deleted (required)
     * @return Invalid order ID (status code 400)
     * @see OrdersApi#deleteOrder
     */
    default ResponseEntity<Void> deleteOrder(UUID id) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /orders/findAll : List all orders
     *
     * @return Orders have been successfully listed (status code 200)
     *         or The resource path is incorrect (status code 404)
     *         or Internal Server Error (status code 500)
     * @see OrdersApi#findAllOrders
     */
    default ResponseEntity<List<Orders>> findAllOrders() {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "[ { \"quantity\" : 85, \"user_id\" : \"2be3e28c-53fb-482a-8cfd-85e168e99bed\", \"product_id\" : \"a320b172-4394-4b9a-8eb3-2fd999fdc460\", \"id\" : \"6574214f-89e3-4001-b54d-0d185163f7a2\", \"status\" : \"Shipped\" }, { \"quantity\" : 85, \"user_id\" : \"2be3e28c-53fb-482a-8cfd-85e168e99bed\", \"product_id\" : \"a320b172-4394-4b9a-8eb3-2fd999fdc460\", \"id\" : \"6574214f-89e3-4001-b54d-0d185163f7a2\", \"status\" : \"Shipped\" } ]";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/xml"))) {
                    String exampleString = "<order> <id>6574214f-89e3-4001-b54d-0d185163f7a2</id> <user_id>2be3e28c-53fb-482a-8cfd-85e168e99bed</user_id> <product_id>a320b172-4394-4b9a-8eb3-2fd999fdc460</product_id> <quantity>85</quantity> <status>Shipped</status> </order>";
                    ApiUtil.setExampleResponse(request, "application/xml", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /orders/findByIDs : Find orders by order IDs
     * Multiple order IDs can be provided with comma separated strings
     *
     * @param orderIDs  (optional)
     * @return Orders have been successfully found by IDs (status code 200)
     *         or Invalid order ID (status code 400)
     * @see OrdersApi#findOrdersById
     */
    default ResponseEntity<List<Orders>> findOrdersById(String orderIDs) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "[ { \"quantity\" : 85, \"user_id\" : \"2be3e28c-53fb-482a-8cfd-85e168e99bed\", \"product_id\" : \"a320b172-4394-4b9a-8eb3-2fd999fdc460\", \"id\" : \"6574214f-89e3-4001-b54d-0d185163f7a2\", \"status\" : \"Shipped\" }, { \"quantity\" : 85, \"user_id\" : \"2be3e28c-53fb-482a-8cfd-85e168e99bed\", \"product_id\" : \"a320b172-4394-4b9a-8eb3-2fd999fdc460\", \"id\" : \"6574214f-89e3-4001-b54d-0d185163f7a2\", \"status\" : \"Shipped\" } ]";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/xml"))) {
                    String exampleString = "<order> <id>6574214f-89e3-4001-b54d-0d185163f7a2</id> <user_id>2be3e28c-53fb-482a-8cfd-85e168e99bed</user_id> <product_id>a320b172-4394-4b9a-8eb3-2fd999fdc460</product_id> <quantity>85</quantity> <status>Shipped</status> </order>";
                    ApiUtil.setExampleResponse(request, "application/xml", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /orders/{id} : Find order by order ID
     * Returns a single order
     *
     * @param id Order ID to return (required)
     * @return Order found by ID (status code 200)
     *         or Invalid order ID (status code 400)
     *         or Order not found (status code 404)
     * @see OrdersApi#getOrderById
     */
    default ResponseEntity<Orders> getOrderById(UUID id) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"quantity\" : 85, \"user_id\" : \"2be3e28c-53fb-482a-8cfd-85e168e99bed\", \"product_id\" : \"a320b172-4394-4b9a-8eb3-2fd999fdc460\", \"id\" : \"6574214f-89e3-4001-b54d-0d185163f7a2\", \"status\" : \"Shipped\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/xml"))) {
                    String exampleString = "<order> <id>6574214f-89e3-4001-b54d-0d185163f7a2</id> <user_id>2be3e28c-53fb-482a-8cfd-85e168e99bed</user_id> <product_id>a320b172-4394-4b9a-8eb3-2fd999fdc460</product_id> <quantity>85</quantity> <status>Shipped</status> </order>";
                    ApiUtil.setExampleResponse(request, "application/xml", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * PUT /orders : Update an existing order
     * Update an existing order by Id
     *
     * @param orders Update an existing order (required)
     * @return Order has been successfully updated (status code 200)
     *         or Invalid ID supplied (status code 400)
     *         or Order not found (status code 404)
     *         or Validation exception (status code 422)
     * @see OrdersApi#updateOrder
     */
    default ResponseEntity<ApiResponseSchema> updateOrder(Orders orders) {
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
     * POST /orders/{id} : Updates an order with form data
     * 
     *
     * @param id ID of the order that needs to be updated (required)
     * @param quantity Quantity for the product of the order that needs to be updated (optional)
     * @param status Status of the order that needs to be updated (optional)
     * @return Invalid input (status code 400)
     * @see OrdersApi#updateOrderWithForm
     */
    default ResponseEntity<Void> updateOrderWithForm(UUID id,
        Integer quantity,
        String status) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
