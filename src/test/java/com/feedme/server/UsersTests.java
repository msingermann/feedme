package com.feedme.server;

import com.feedme.server.model.CreateUserRequest;
import com.feedme.server.model.LoginUserRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class UsersTests extends IntegrationTests {


    @Test
    public void createUser() {
        CreateUserRequest payload = new CreateUserRequest("user", "pa$$word");
        RestAssured.given().port(port).contentType(ContentType.JSON).body(payload).post(USERS_PATH).then()
                .statusCode(HttpStatus.SC_OK)
                .and().body("id", notNullValue());
    }

    @Test
    public void loginUser() {
        CreateUserRequest payload = new CreateUserRequest("user3", "pa$$word");
        RestAssured.given().port(port).contentType(ContentType.JSON).body(payload).post(USERS_PATH).then()
                .statusCode(HttpStatus.SC_OK)
                .and().body("id", notNullValue());

        LoginUserRequest loginPayload = new LoginUserRequest("user3", "pa$$word");
        RestAssured.given().port(port).contentType(ContentType.JSON).given().body(loginPayload).post(LOGIN_PATH).then()
                .statusCode(HttpStatus.SC_OK)
                .and().body("token", notNullValue());
    }

    @Test
    public void usernameAlreadyTaken() {
        String username = "usertaken";
        CreateUserRequest payload = new CreateUserRequest(username, "pa$$word");
        RestAssured.given().port(port).contentType(ContentType.JSON).body(payload).post(USERS_PATH).then()
                .statusCode(HttpStatus.SC_OK)
                .and().body("id", notNullValue());

        RestAssured.given().port(port).contentType(ContentType.JSON).body(payload).post(USERS_PATH).then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .and().body("message", is("Name " + username + " is already taken."));
    }

    @Test
    public void getuser() {
        CreateUserRequest payload = new CreateUserRequest("user4", "pa$$word");
        RestAssured.given().port(port).contentType(ContentType.JSON).body(payload).post(USERS_PATH).then()
                .statusCode(HttpStatus.SC_OK)
                .and().body("id", notNullValue());

        LoginUserRequest loginPayload = new LoginUserRequest("user4", "pa$$word");
        UUID token = RestAssured.given().port(port).
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

        RestAssured.given().port(port).contentType(ContentType.JSON).given().body(loginPayload)
                .header(HttpHeaders.AUTHORIZATION, "bearer " + token)
                .get(USERS_PATH)
                .then()
                .log().body()
                .statusCode(HttpStatus.SC_OK)
                .and().body("id", notNullValue())
                .and().body("username", is("user4"));

    }


}


