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

public class LoginUserTest {
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
    @DisplayName("Корректная авторизация")
    public void loginUserTest() {
        ValidatableResponse loginUserResponse = userClient.loginUser(user.getEmail(), user.getPassword());
        loginUserResponse.assertThat().statusCode(SC_OK).body("success", is(true));
    }

    @After
    public void cleanUp() {
        userClient.deleteUser(accessToken);
    }
}
