package com.feedme.server;

import com.feedme.server.model.CreateFeederRequest;
import com.feedme.server.model.CreatePetRequest;
import com.feedme.server.model.LoginUserRequest;
import com.feedme.server.model.User;
import com.feedme.server.repositories.UsersRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.hamcrest.Matchers.notNullValue;

public class PetsTests extends IntegrationTests {

    private UUID token;
    @Autowired
    private UsersRepository usersRepository;

    @BeforeEach
    public void initUser() {
        User user = usersRepository.findById(1L)
                .orElseGet(() -> usersRepository.save(new User("feedersUser", "pa$$word")));
        LoginUserRequest loginPayload = new LoginUserRequest(user.getUsername(), user.getPassword());
        token = RestAssured.given().port(port).
                contentType(ContentType.JSON)
                .body(loginPayload)
                .post(LOGIN_PATH)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and().body("token", notNullValue())
                .extract()
                .body()
                .jsonPath()
                .getUUID("token");
    }

    @Test
    public void createPet() {
        CreatePetRequest payload = new CreatePetRequest("MyPet1", "tagId1");
        RestAssured.given().port(port)
                .contentType(ContentType.JSON)
                .body(payload)
                .header(HttpHeaders.AUTHORIZATION, "bearer " + token)
                .post(CREATE_PET_PATH)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and().body("id", notNullValue());
    }

    @Test
    public void userDontExists() {
        CreatePetRequest payload = new CreatePetRequest("MyPet2", "tagId1");
        RestAssured.given().port(port)
                .contentType(ContentType.JSON)
                .body(payload)
                .post(CREATE_PET_PATH)
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }

    @Test
    public void getPet() {
        CreatePetRequest payload = new CreatePetRequest("MyPet3", "tagId1");
        UUID petId = RestAssured.given().port(port)
                .contentType(ContentType.JSON)
                .body(payload)
                .header(HttpHeaders.AUTHORIZATION, "bearer " + token)
                .post(CREATE_PET_PATH).then()
                .statusCode(HttpStatus.SC_OK)
                .and().body("id", notNullValue())
                .extract()
                .body()
                .jsonPath()
                .getUUID("id");

        RestAssured.given().port(port)
                .contentType(ContentType.JSON)
                .given()
                .body(payload)
                .header(HttpHeaders.AUTHORIZATION, "bearer " + token)
                .get(String.format(GET_PET_PATH_TEMPLATE, petId))
                .then().log().body()
                .statusCode(HttpStatus.SC_OK)
                .and().body("id", notNullValue());
    }

    @Test
    public void getFromDifferentUserShouldNotFound() {
        User user2 = usersRepository.save(new User("petsUser2", "pa$$word"));
        LoginUserRequest loginPayload = new LoginUserRequest(user2.getUsername(), user2.getPassword());
        UUID token2 = RestAssured.given().port(port)
                .contentType(ContentType.JSON)
                .body(loginPayload)
                .post(LOGIN_PATH)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and().body("token", notNullValue())
                .extract()
                .body()
                .jsonPath()
                .getUUID("token");

        // Create with user1
        CreatePetRequest payload = new CreatePetRequest("MyPet4", "tagId1");
        UUID petId = RestAssured.given().port(port)
                .contentType(ContentType.JSON)
                .body(payload)
                .header(HttpHeaders.AUTHORIZATION, "bearer " + token)
                .post(CREATE_PET_PATH)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and().body("id", notNullValue())
                .extract()
                .body()
                .jsonPath()
                .getUUID("id");

        // Consume with user2
        RestAssured.given().port(port)
                .contentType(ContentType.JSON)
                .given()
                .body(payload)
                .header(HttpHeaders.AUTHORIZATION, "bearer " + token2)
                .get(String.format(GET_PET_PATH_TEMPLATE, petId))
                .then().log().body()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }
}
