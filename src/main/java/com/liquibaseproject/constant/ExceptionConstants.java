package com.liquibaseproject.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class ExceptionConstants {
    public static final String USERNAME_AND_E_MAIL_ARE_MANDATORY = "Username and e-mail are mandatory";
    public static final String NAME_PRICE_AND_QUANTITY_ARE_MANDATORY = "Name, price and quantity are mandatory";
    public static final String USERS_NOT_FOUND_EXCEPTION_MESSAGE = "No users found for the provided input";
    public static final String PRODUCTS_NOT_FOUND_EXCEPTION_MESSAGE = "No products found for the provided input";
    public static final String ORDERS_NOT_FOUND_EXCEPTION_MESSAGE = "No orders found for the provided input";
    public static final String USER_ID_PRODUCT_ID_AND_QUANTITY_ARE_MANDATORY_MESSAGE = "User ID, Product ID and quantity are mandatory";
    public static final String DATA_INTEGRITY_VIOLATION_PRODUCT_MESSAGE = "Error occurred while deleting a product";
    public static final String DATA_INTEGRITY_VIOLATION_USER_MESSAGE = "Error occurred while deleting a user";
    public static final String INTERNAL_SERVER_ERROR_MESSAGE = "Something went wrong on the server side";
    public static final String NO_ACCESS_TO_PERFORM_THIS_OPERATION_MESSAGE = "You don't have access to perform this operation";
}