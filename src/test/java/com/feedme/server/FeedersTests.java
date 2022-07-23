package com.feedme.server;

import com.feedme.server.model.CreateFeederRequest;
import com.feedme.server.model.LoginUserRequest;
import com.feedme.server.model.User;
import com.feedme.server.repositories.UsersRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.hamcrest.Matchers.*;

public class FeedersTests extends IntegrationTests {

    private UUID token;
    @Autowired
    private UsersRepository usersRepository;

    @BeforeEach
    public void initUser() {
        User user = usersRepository.findById(1L)
                .orElseGet(() -> usersRepository.save(new User("feedersUser@domain.com", "pa$$word")));
        LoginUserRequest loginPayload = new LoginUserRequest(user.getEmail(), user.getPassword());
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
    public void registerFeeder() {
        CreateFeederRequest payload = new CreateFeederRequest("mac111111", "MyFeeder");
        RestAssured.given().port(port)
                .contentType(ContentType.JSON)
                .body(payload)
                .header(HttpHeaders.AUTHORIZATION, "bearer " + token)
                .post(FEEDERS_PATH)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and().body("id", notNullValue());
    }

    @Test
    public void userDontExists() {
        CreateFeederRequest payload = new CreateFeederRequest("mac22222", "MyFeeder");
        RestAssured.given().port(port)
                .contentType(ContentType.JSON)
                .body(payload)
                .post(FEEDERS_PATH)
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }

    @Test
    public void getFeeder() {
        CreateFeederRequest payload = new CreateFeederRequest("mac33333", "MyFeeder");
        UUID feederId = RestAssured.given().port(port)
                .contentType(ContentType.JSON)
                .body(payload)
                .header(HttpHeaders.AUTHORIZATION, "bearer " + token)
                .post(FEEDERS_PATH).then()
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
                .get(String.format(GET_FEEDER_PATH_TEMPLATE, feederId))
                .then().log().body()
                .statusCode(HttpStatus.SC_OK)
                .and().body("id", notNullValue())
                .and().body("annexes", notNullValue());

    }

    @Test
    public void getFromDifferentUserShouldNotFound() {
        User user2 = usersRepository.save(new User("feedersUser2@domain.com", "pa$$word"));
        LoginUserRequest loginPayload = new LoginUserRequest(user2.getEmail(), user2.getPassword());
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
        CreateFeederRequest payload = new CreateFeederRequest("mac44444", "MyFeeder");
        UUID feederId = RestAssured.given().port(port)
                .contentType(ContentType.JSON)
                .body(payload)
                .header(HttpHeaders.AUTHORIZATION, "bearer " + token)
                .post(FEEDERS_PATH)
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
                .get(String.format(GET_FEEDER_PATH_TEMPLATE, feederId))
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }


    @Test
    public void getFeeders() {
        CreateFeederRequest payload = new CreateFeederRequest("mac5555", "MyFeeder");
        UUID feederId = RestAssured.given().port(port)
                .contentType(ContentType.JSON)
                .body(payload)
                .header(HttpHeaders.AUTHORIZATION, "bearer " + token)
                .post(FEEDERS_PATH).then()
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
                .get(String.format(FEEDERS_PATH, feederId))
                .then().log().body()
                .statusCode(HttpStatus.SC_OK);

    }

}
