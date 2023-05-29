package client;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.client.User;
import org.example.client.UserClient;
import org.example.client.UserGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.is;

public class CreateUserTest {
    private UserClient userClient;
    private User user;
    private String accessToken;

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = UserGenerator.getRandomUser();
    }

    @Test
    @DisplayName("Создание пользователя с валидными данными")
    public void createValidUserTest() {
        ValidatableResponse createUserResponse = userClient.createUser(user);
        createUserResponse.assertThat().statusCode(SC_OK).body("success", is(true));
        accessToken = createUserResponse.extract().path("accessToken");
    }

    @Test
    @DisplayName("Создание пользователя с неуникальным логином")
    public void createNotUniqueUserTest() {
        accessToken = userClient.createUser(user).extract().path("accessToken");
        ValidatableResponse createUserResponse = userClient.createUser(user);
        createUserResponse.assertThat().statusCode(SC_FORBIDDEN).body("success", is(false));
    }

    @After
    public void cleanUp() {
        userClient.deleteUser(accessToken);
    }
}
