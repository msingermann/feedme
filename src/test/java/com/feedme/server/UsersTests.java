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
        CreateUserRequest payload = new CreateUserRequest("user@domain.com", "pa$$word");
        RestAssured.given().port(port).contentType(ContentType.JSON).body(payload).post(USERS_PATH).then()
                .statusCode(HttpStatus.SC_OK)
                .and().body("id", notNullValue());
    }

    @Test
    public void invalidEmail() {
        String email = "invalid@email";
        CreateUserRequest payload = new CreateUserRequest(email, "pa$$word");
        RestAssured.given().port(port).contentType(ContentType.JSON).body(payload).post(USERS_PATH).then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .and().body("message", is("Email " + email + " is not valid."));
    }

    @Test
    public void loginUser() {
        CreateUserRequest payload = new CreateUserRequest("user3@domain.com", "pa$$word");
        RestAssured.given().port(port).contentType(ContentType.JSON).body(payload).post(USERS_PATH).then()
                .statusCode(HttpStatus.SC_OK)
                .and().body("id", notNullValue());

        LoginUserRequest loginPayload = new LoginUserRequest("user3@domain.com", "pa$$word");
        RestAssured.given().port(port).contentType(ContentType.JSON).given().body(loginPayload).post(LOGIN_PATH).then()
                .statusCode(HttpStatus.SC_OK)
                .and().body("token", notNullValue());
    }

    @Test
    public void emailAlreadyTaken() {
        String email = "usertaken@domain.com";
        CreateUserRequest payload = new CreateUserRequest(email, "pa$$word");
        RestAssured.given().port(port).contentType(ContentType.JSON).body(payload).post(USERS_PATH).then()
                .statusCode(HttpStatus.SC_OK)
                .and().body("id", notNullValue());

        RestAssured.given().port(port).contentType(ContentType.JSON).body(payload).post(USERS_PATH).then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .and().body("message", is("Email " + email + " is already taken."));
    }

    @Test
    public void getUser() {
        CreateUserRequest payload = new CreateUserRequest("user4@domain.com", "pa$$word");
        RestAssured.given().port(port).contentType(ContentType.JSON).body(payload).post(USERS_PATH).then()
                .statusCode(HttpStatus.SC_OK)
                .and().body("id", notNullValue());

        LoginUserRequest loginPayload = new LoginUserRequest("user4@domain.com", "pa$$word");
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
                .statusCode(HttpStatus.SC_OK)
                .and().body("id", notNullValue())
                .and().body("email", is("user4@domain.com"));

    }


}


