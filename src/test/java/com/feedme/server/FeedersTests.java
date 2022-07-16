package com.feedme.server;

import com.feedme.server.model.CreateFeederRequest;
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

public class FeedersTests extends IntegrationTests {

    private User user;
    @Autowired
    private UsersRepository usersRepository;

    @BeforeEach
    public void initUser() {
        user = usersRepository.findById(1L)
                .orElseGet(() -> usersRepository.save(new User("feedersUser", "pa$$word")));
    }

    @Test
    public void registerFeeder() {
        CreateFeederRequest payload = new CreateFeederRequest("mac1321321", "MyFeeder");
        RestAssured.given().port(port).contentType(ContentType.JSON).given().body(payload).header(HttpHeaders.AUTHORIZATION, "bearer " + user.getId()).post(REGISTER_FEEDERS_PATH).then()
                .statusCode(HttpStatus.SC_OK)
                .and().body("id", notNullValue());
    }

    @Test
    public void userDontExists() {
        CreateFeederRequest payload = new CreateFeederRequest("mac123123", "MyFeeder");
        RestAssured.given().port(port).contentType(ContentType.JSON).given().body(payload).post(REGISTER_FEEDERS_PATH).then()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }

    @Test
    public void getFeeder() {
        CreateFeederRequest payload = new CreateFeederRequest("mac222222", "MyFeeder");
        UUID feederId = RestAssured.given().port(port).contentType(ContentType.JSON).given().body(payload).header(HttpHeaders.AUTHORIZATION, "bearer " + user.getId()).post(REGISTER_FEEDERS_PATH).then()
                .statusCode(HttpStatus.SC_OK)
                .and().body("id", notNullValue())
                .extract()
                .body()
                .jsonPath()
                .getUUID("id");

        RestAssured.given().port(port).contentType(ContentType.JSON)
                .given()
                .body(payload)
                .header(HttpHeaders.AUTHORIZATION, "bearer " + user.getId())
                .get(String.format(GET_FEEDER_PATH_TEMPLATE, feederId))
                .then().log().body()
                .statusCode(HttpStatus.SC_OK)
                .and().body("id", notNullValue());
    }
    @Test
    public void getFromDifferentUserShouldNotFound() {
        User user2 = usersRepository.save(new User("feedersUser2", "pa$$word"));
        //TODO: login user 2 and get token

        // Create with user1
        CreateFeederRequest payload = new CreateFeederRequest("mac123123", "MyFeeder");
        UUID feederId = RestAssured.given().port(port).contentType(ContentType.JSON).given().body(payload).header(HttpHeaders.AUTHORIZATION, "bearer " + user.getId()).post(REGISTER_FEEDERS_PATH).then()
                .statusCode(HttpStatus.SC_OK)
                .and().body("id", notNullValue())
                .extract()
                .body()
                .jsonPath()
                .getUUID("id");

        // Consume with user2
        RestAssured.given().port(port).contentType(ContentType.JSON)
                .given()
                .body(payload)
                .header(HttpHeaders.AUTHORIZATION, "bearer " + user2.getId())
                .get(String.format(GET_FEEDER_PATH_TEMPLATE, feederId))
                .then().log().body()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }
}
