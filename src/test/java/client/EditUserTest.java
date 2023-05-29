package client;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.client.User;
import org.example.client.UserClient;
import org.example.client.UserGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.is;

public class EditUserTest {
    private UserClient userClient;
    private User user;
    private String accessToken;

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = UserGenerator.getRandomUser();
        accessToken = userClient.createUser(user).extract().path("accessToken");
    }

    @Test
    @DisplayName("Изменение пользователя с авторизацией")
    public void editUserWithLoginTest() {

        ValidatableResponse editUserResponse = userClient.editUser(accessToken,
                RandomStringUtils.randomAlphabetic(1, 5) + "@yandex.ru",
                RandomStringUtils.randomAlphabetic(6, 15),
                RandomStringUtils.randomAlphabetic(1, 12));
        editUserResponse.assertThat().statusCode(SC_OK).body("success", is(true));
    }

    @Test
    @DisplayName("Изменение пользователя с некорректной авторизацией")
    public void editUserWithoutLoginTest() {

        ValidatableResponse editUserResponse = userClient.editUser("",
                "changeEmail@mail.ru", "changePassword", "changeName");
        editUserResponse.assertThat().statusCode(SC_UNAUTHORIZED).body("success", is(false));
    }

    @After
    public void cleanUp() {
        userClient.deleteUser(accessToken);
    }

}
