/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (7.7.0).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package com.liquibaseproject.api;

import com.liquibaseproject.model.ModelApiResponse;
import java.util.UUID;
import com.liquibaseproject.model.Users;
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

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-08-09T09:14:07.858132100+02:00[Europe/Budapest]", comments = "Generator version: 7.7.0")
@Validated
@Tag(name = "users", description = "List of users")
public interface UsersApi {

    default UsersApiDelegate getDelegate() {
        return new UsersApiDelegate() {};
    }

    /**
     * POST /users : Add a new user
     * Add a new user
     *
     * @param users Create a new user (required)
     * @return User has been successfully added (status code 200)
     *         or Invalid input (status code 400)
     *         or Validation exception (status code 422)
     */
    @Operation(
        operationId = "addUser",
        summary = "Add a new user",
        description = "Add a new user",
        tags = { "users" },
        responses = {
            @ApiResponse(responseCode = "200", description = "User has been successfully added", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Users.class)),
                @Content(mediaType = "application/xml", schema = @Schema(implementation = Users.class))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "422", description = "Validation exception")
        },
        security = {
            @SecurityRequirement(name = "petstore_auth")
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/users",
        produces = { "application/json", "application/xml" },
        consumes = { "application/json", "application/xml", "application/x-www-form-urlencoded" }
    )
    
    default ResponseEntity<Users> addUser(
        @Parameter(name = "Users", description = "Create a new user", required = true) @Valid @RequestBody Users users
    ) {
        return getDelegate().addUser(users);
    }


    /**
     * DELETE /users/{ID} : Deletes a user
     * delete a user
     *
     * @param ID User ID for the user that needs to be deleted (required)
     * @param apiKey  (optional)
     * @return Invalid user ID value (status code 400)
     */
    @Operation(
        operationId = "deleteUser",
        summary = "Deletes a user",
        description = "delete a user",
        tags = { "users" },
        responses = {
            @ApiResponse(responseCode = "400", description = "Invalid user ID value")
        },
        security = {
            @SecurityRequirement(name = "petstore_auth")
        }
    )
    @RequestMapping(
        method = RequestMethod.DELETE,
        value = "/users/{ID}"
    )
    
    default ResponseEntity<Void> deleteUser(
        @Parameter(name = "ID", description = "User ID for the user that needs to be deleted", required = true, in = ParameterIn.PATH) @PathVariable("ID") UUID ID,
        @Parameter(name = "api_key", description = "", in = ParameterIn.HEADER) @RequestHeader(value = "api_key", required = false) String apiKey
    ) {
        return getDelegate().deleteUser(ID, apiKey);
    }


    /**
     * GET /users/findByUsername : Find users by username
     * Multiple usernames can be provided with comma separated strings
     *
     * @param username  (optional)
     * @return Users have been successfully found by username (status code 200)
     *         or Invalid username (status code 400)
     */
    @Operation(
        operationId = "findUsersByUsername",
        summary = "Find users by username",
        description = "Multiple usernames can be provided with comma separated strings",
        tags = { "users" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Users have been successfully found by username", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Users.class))),
                @Content(mediaType = "application/xml", array = @ArraySchema(schema = @Schema(implementation = Users.class)))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid username")
        },
        security = {
            @SecurityRequirement(name = "petstore_auth")
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/users/findByUsername",
        produces = { "application/json", "application/xml" }
    )
    
    default ResponseEntity<List<Users>> findUsersByUsername(
        @Parameter(name = "username", description = "", in = ParameterIn.QUERY) @Valid @RequestParam(value = "username", required = false) String username
    ) {
        return getDelegate().findUsersByUsername(username);
    }


    /**
     * GET /users/{ID} : Find user by ID
     * Returns a single user
     *
     * @param ID User ID to return (required)
     * @return User found by ID (status code 200)
     *         or Invalid user ID (status code 400)
     *         or User not found (status code 404)
     */
    @Operation(
        operationId = "getUserById",
        summary = "Find user by ID",
        description = "Returns a single user",
        tags = { "users" },
        responses = {
            @ApiResponse(responseCode = "200", description = "User found by ID", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Users.class)),
                @Content(mediaType = "application/xml", schema = @Schema(implementation = Users.class))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid user ID"),
            @ApiResponse(responseCode = "404", description = "User not found")
        },
        security = {
            @SecurityRequirement(name = "petstore_auth"),
            @SecurityRequirement(name = "api_key")
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/users/{ID}",
        produces = { "application/json", "application/xml" }
    )
    
    default ResponseEntity<Users> getUserById(
        @Parameter(name = "ID", description = "User ID to return", required = true, in = ParameterIn.PATH) @PathVariable("ID") UUID ID
    ) {
        return getDelegate().getUserById(ID);
    }


    /**
     * PUT /users : Update an existing user
     * Update an existing user by Id
     *
     * @param users Update an existent user (required)
     * @return User has been successfully edited (status code 200)
     *         or Invalid ID supplied (status code 400)
     *         or User not found (status code 404)
     *         or Validation exception (status code 422)
     */
    @Operation(
        operationId = "updateUser",
        summary = "Update an existing user",
        description = "Update an existing user by Id",
        tags = { "users" },
        responses = {
            @ApiResponse(responseCode = "200", description = "User has been successfully edited", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ModelApiResponse.class)),
                @Content(mediaType = "application/xml", schema = @Schema(implementation = ModelApiResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "422", description = "Validation exception")
        },
        security = {
            @SecurityRequirement(name = "petstore_auth")
        }
    )
    @RequestMapping(
        method = RequestMethod.PUT,
        value = "/users",
        produces = { "application/json", "application/xml" },
        consumes = { "application/json", "application/xml", "application/x-www-form-urlencoded" }
    )
    
    default ResponseEntity<ModelApiResponse> updateUser(
        @Parameter(name = "Users", description = "Update an existent user", required = true) @Valid @RequestBody Users users
    ) {
        return getDelegate().updateUser(users);
    }


    /**
     * POST /users/{ID} : Updates a user with form data
     * 
     *
     * @param ID ID of user that needs to be updated (required)
     * @param username Username for the user that needs to be updated (optional)
     * @param email E-mail of the user that needs to be updated (optional)
     * @return Invalid input (status code 400)
     */
    @Operation(
        operationId = "updateUserWithForm",
        summary = "Updates a user with form data",
        description = "",
        tags = { "users" },
        responses = {
            @ApiResponse(responseCode = "400", description = "Invalid input")
        },
        security = {
            @SecurityRequirement(name = "petstore_auth")
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/users/{ID}"
    )
    
    default ResponseEntity<Void> updateUserWithForm(
        @Parameter(name = "ID", description = "ID of user that needs to be updated", required = true, in = ParameterIn.PATH) @PathVariable("ID") UUID ID,
        @Parameter(name = "username", description = "Username for the user that needs to be updated", in = ParameterIn.QUERY) @Valid @RequestParam(value = "username", required = false) String username,
        @Parameter(name = "email", description = "E-mail of the user that needs to be updated", in = ParameterIn.QUERY) @Valid @RequestParam(value = "email", required = false) String email
    ) {
        return getDelegate().updateUserWithForm(ID, username, email);
    }

}
