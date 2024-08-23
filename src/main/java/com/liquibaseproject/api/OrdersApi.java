/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (7.7.0).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package com.liquibaseproject.api;

import com.liquibaseproject.model.ApiResponseSchema;
import com.liquibaseproject.model.NewOrder;
import com.liquibaseproject.model.Orders;
import java.util.UUID;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;
import java.util.Map;
import jakarta.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.7.0")
@Validated
@Tag(name = "orders", description = "List of orders")
public interface OrdersApi {

    default OrdersApiDelegate getDelegate() {
        return new OrdersApiDelegate() {};
    }

    /**
     * POST /orders : Add a new order
     * Add a new order
     *
     * @param newOrder Place a new order (required)
     * @return Order has been successfully placed (status code 200)
     *         or Access forbidden (status code 403)
     */
    @Operation(
        operationId = "addOrder",
        summary = "Add a new order",
        description = "Add a new order",
        tags = { "orders" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Order has been successfully placed", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Orders.class)),
                @Content(mediaType = "application/xml", schema = @Schema(implementation = Orders.class))
            }),
            @ApiResponse(responseCode = "403", description = "Access forbidden", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseSchema.class)),
                @Content(mediaType = "application/xml", schema = @Schema(implementation = ApiResponseSchema.class))
            })
        },
        security = {
            @SecurityRequirement(name = "basicAuth")
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/orders",
        produces = { "application/json", "application/xml" },
        consumes = { "application/json", "application/xml", "application/x-www-form-urlencoded" }
    )
    
    default ResponseEntity<Orders> addOrder(
        @Parameter(name = "NewOrder", description = "Place a new order", required = true) @Valid @RequestBody NewOrder newOrder
    ) {
        return getDelegate().addOrder(newOrder);
    }


    /**
     * DELETE /orders/{id} : Deletes an order
     * delete an order
     *
     * @param id ID of the order that needs to be deleted (required)
     * @return Order found by ID (status code 200)
     */
    @Operation(
        operationId = "deleteOrder",
        summary = "Deletes an order",
        description = "delete an order",
        tags = { "orders" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Order found by ID", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseSchema.class)),
                @Content(mediaType = "application/xml", schema = @Schema(implementation = ApiResponseSchema.class))
            })
        },
        security = {
            @SecurityRequirement(name = "basicAuth")
        }
    )
    @RequestMapping(
        method = RequestMethod.DELETE,
        value = "/orders/{id}",
        produces = { "application/json", "application/xml" }
    )
    
    default ResponseEntity<ApiResponseSchema> deleteOrder(
        @Parameter(name = "id", description = "ID of the order that needs to be deleted", required = true, in = ParameterIn.PATH) @PathVariable("id") UUID id
    ) {
        return getDelegate().deleteOrder(id);
    }


    /**
     * GET /orders/findAll : List all orders
     *
     * @return Orders have been successfully listed (status code 200)
     */
    @Operation(
        operationId = "findAllOrders",
        summary = "List all orders",
        tags = { "orders" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Orders have been successfully listed", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Orders.class))),
                @Content(mediaType = "application/xml", array = @ArraySchema(schema = @Schema(implementation = Orders.class)))
            })
        },
        security = {
            @SecurityRequirement(name = "basicAuth")
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/orders/findAll",
        produces = { "application/json", "application/xml" }
    )
    
    default ResponseEntity<List<Orders>> findAllOrders(
        
    ) {
        return getDelegate().findAllOrders();
    }


    /**
     * GET /orders/findByIDs : Find orders by order IDs
     * Multiple order IDs can be provided with comma separated strings
     *
     * @param orderIDs  (optional)
     * @return Orders have been successfully found by IDs (status code 200)
     */
    @Operation(
        operationId = "findOrdersById",
        summary = "Find orders by order IDs",
        description = "Multiple order IDs can be provided with comma separated strings",
        tags = { "orders" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Orders have been successfully found by IDs", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Orders.class))),
                @Content(mediaType = "application/xml", array = @ArraySchema(schema = @Schema(implementation = Orders.class)))
            })
        },
        security = {
            @SecurityRequirement(name = "basicAuth")
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/orders/findByIDs",
        produces = { "application/json", "application/xml" }
    )
    
    default ResponseEntity<List<Orders>> findOrdersById(
        @Parameter(name = "orderIDs", description = "", in = ParameterIn.QUERY) @Valid @RequestParam(value = "orderIDs", required = false) String orderIDs
    ) {
        return getDelegate().findOrdersById(orderIDs);
    }


    /**
     * GET /orders/{id} : Find order by order ID
     * Returns a single order
     *
     * @param id Order ID to return (required)
     * @return Order found by ID (status code 200)
     */
    @Operation(
        operationId = "getOrderById",
        summary = "Find order by order ID",
        description = "Returns a single order",
        tags = { "orders" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Order found by ID", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Orders.class)),
                @Content(mediaType = "application/xml", schema = @Schema(implementation = Orders.class))
            })
        },
        security = {
            @SecurityRequirement(name = "basicAuth")
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/orders/{id}",
        produces = { "application/json", "application/xml" }
    )
    
    default ResponseEntity<Orders> getOrderById(
        @Parameter(name = "id", description = "Order ID to return", required = true, in = ParameterIn.PATH) @PathVariable("id") UUID id
    ) {
        return getDelegate().getOrderById(id);
    }


    /**
     * PUT /orders : Update an existing order
     * Update an existing order by Id
     *
     * @param orders Update an existing order (required)
     * @return Order has been successfully updated (status code 200)
     *         or Access forbidden (status code 403)
     */
    @Operation(
        operationId = "updateOrder",
        summary = "Update an existing order",
        description = "Update an existing order by Id",
        tags = { "orders" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Order has been successfully updated", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseSchema.class)),
                @Content(mediaType = "application/xml", schema = @Schema(implementation = ApiResponseSchema.class))
            }),
            @ApiResponse(responseCode = "403", description = "Access forbidden", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseSchema.class)),
                @Content(mediaType = "application/xml", schema = @Schema(implementation = ApiResponseSchema.class))
            })
        },
        security = {
            @SecurityRequirement(name = "basicAuth")
        }
    )
    @RequestMapping(
        method = RequestMethod.PUT,
        value = "/orders",
        produces = { "application/json", "application/xml" },
        consumes = { "application/json", "application/xml", "application/x-www-form-urlencoded" }
    )
    
    default ResponseEntity<ApiResponseSchema> updateOrder(
        @Parameter(name = "Orders", description = "Update an existing order", required = true) @Valid @RequestBody Orders orders
    ) {
        return getDelegate().updateOrder(orders);
    }


    /**
     * POST /orders/{id} : Updates an order with form data
     * 
     *
     * @param id ID of the order that needs to be updated (required)
     * @param quantity Quantity for the product of the order that needs to be updated (optional)
     * @param status Status of the order that needs to be updated (optional)
     * @return Order found by ID (status code 200)
     *         or Access forbidden (status code 403)
     */
    @Operation(
        operationId = "updateOrderWithForm",
        summary = "Updates an order with form data",
        description = "",
        tags = { "orders" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Order found by ID", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseSchema.class)),
                @Content(mediaType = "application/xml", schema = @Schema(implementation = ApiResponseSchema.class))
            }),
            @ApiResponse(responseCode = "403", description = "Access forbidden", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseSchema.class)),
                @Content(mediaType = "application/xml", schema = @Schema(implementation = ApiResponseSchema.class))
            })
        },
        security = {
            @SecurityRequirement(name = "basicAuth")
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/orders/{id}",
        produces = { "application/json", "application/xml" }
    )
    
    default ResponseEntity<ApiResponseSchema> updateOrderWithForm(
        @Parameter(name = "id", description = "ID of the order that needs to be updated", required = true, in = ParameterIn.PATH) @PathVariable("id") UUID id,
        @Parameter(name = "quantity", description = "Quantity for the product of the order that needs to be updated", in = ParameterIn.QUERY) @Valid @RequestParam(value = "quantity", required = false) Integer quantity,
        @Parameter(name = "status", description = "Status of the order that needs to be updated", in = ParameterIn.QUERY) @Valid @RequestParam(value = "status", required = false) String status
    ) {
        return getDelegate().updateOrderWithForm(id, quantity, status);
    }

}
