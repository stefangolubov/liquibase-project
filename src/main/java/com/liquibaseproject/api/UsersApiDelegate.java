package com.liquibaseproject.api;

import com.liquibaseproject.model.ModelApiResponse;
import java.util.UUID;
import com.liquibaseproject.model.Users;
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
 * A delegate to be called by the {@link UsersApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-08-09T09:14:07.858132100+02:00[Europe/Budapest]", comments = "Generator version: 7.7.0")
public interface UsersApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * POST /users : Add a new user
     * Add a new user
     *
     * @param users Create a new user (required)
     * @return User has been successfully added (status code 200)
     *         or Invalid input (status code 400)
     *         or Validation exception (status code 422)
     * @see UsersApi#addUser
     */
    default ResponseEntity<Users> addUser(Users users) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"ID\" : \"6574214f-89e3-4001-b54d-0d185163f7a2\", \"email\" : \"user@username.com\", \"username\" : \"user.name\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/xml"))) {
                    String exampleString = "<user> <ID>6574214f-89e3-4001-b54d-0d185163f7a2</ID> <username>user.name</username> <email>user@username.com</email> </user>";
                    ApiUtil.setExampleResponse(request, "application/xml", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * DELETE /users/{ID} : Deletes a user
     * delete a user
     *
     * @param ID User ID for the user that needs to be deleted (required)
     * @param apiKey  (optional)
     * @return Invalid user ID value (status code 400)
     * @see UsersApi#deleteUser
     */
    default ResponseEntity<Void> deleteUser(UUID ID,
        String apiKey) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /users/findByUsername : Find users by username
     * Multiple usernames can be provided with comma separated strings
     *
     * @param username  (optional)
     * @return Users have been successfully found by username (status code 200)
     *         or Invalid username (status code 400)
     * @see UsersApi#findUsersByUsername
     */
    default ResponseEntity<List<Users>> findUsersByUsername(String username) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "[ { \"ID\" : \"6574214f-89e3-4001-b54d-0d185163f7a2\", \"email\" : \"user@username.com\", \"username\" : \"user.name\" }, { \"ID\" : \"6574214f-89e3-4001-b54d-0d185163f7a2\", \"email\" : \"user@username.com\", \"username\" : \"user.name\" } ]";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/xml"))) {
                    String exampleString = "<user> <ID>6574214f-89e3-4001-b54d-0d185163f7a2</ID> <username>user.name</username> <email>user@username.com</email> </user>";
                    ApiUtil.setExampleResponse(request, "application/xml", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /users/{ID} : Find user by ID
     * Returns a single user
     *
     * @param ID User ID to return (required)
     * @return User found by ID (status code 200)
     *         or Invalid user ID (status code 400)
     *         or User not found (status code 404)
     * @see UsersApi#getUserById
     */
    default ResponseEntity<Users> getUserById(UUID ID) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"ID\" : \"6574214f-89e3-4001-b54d-0d185163f7a2\", \"email\" : \"user@username.com\", \"username\" : \"user.name\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/xml"))) {
                    String exampleString = "<user> <ID>6574214f-89e3-4001-b54d-0d185163f7a2</ID> <username>user.name</username> <email>user@username.com</email> </user>";
                    ApiUtil.setExampleResponse(request, "application/xml", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

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
     * @see UsersApi#updateUser
     */
    default ResponseEntity<ModelApiResponse> updateUser(Users users) {
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
     * POST /users/{ID} : Updates a user with form data
     * 
     *
     * @param ID ID of user that needs to be updated (required)
     * @param username Username for the user that needs to be updated (optional)
     * @param email E-mail of the user that needs to be updated (optional)
     * @return Invalid input (status code 400)
     * @see UsersApi#updateUserWithForm
     */
    default ResponseEntity<Void> updateUserWithForm(UUID ID,
        String username,
        String email) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
