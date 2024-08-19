package com.liquibaseproject.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;
import jakarta.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.7.0")
@Controller
@RequestMapping("${openapi.liquibaseProjectOpenAPI30.base-path:}")
public class OrdersApiController implements OrdersApi {

    private final OrdersApiDelegate delegate;

    public OrdersApiController(@Autowired(required = false) OrdersApiDelegate delegate) {
        this.delegate = Optional.ofNullable(delegate).orElse(new OrdersApiDelegate() {});
    }

    @Override
    public OrdersApiDelegate getDelegate() {
        return delegate;
    }

}