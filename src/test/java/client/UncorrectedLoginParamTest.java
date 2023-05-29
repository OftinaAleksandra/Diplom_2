package client;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.client.User;
import org.example.client.UserClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.Matchers.is;

@RunWith(Parameterized.class)
public class UncorrectedLoginParamTest {
    private UserClient userClient;
    private User user;
    private String email;
    private String password;
    private String name;

    public UncorrectedLoginParamTest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Parameterized.Parameters(name = "Авторизация с почтой {0} и паролем {1}")
    public static Object[][] getUserData() {
        return new Object[][]{
                {RandomStringUtils.randomAlphabetic(1, 5) + "@yandex.ru", RandomStringUtils.randomAlphabetic(6, 15)},
                {RandomStringUtils.randomAlphabetic(1, 5) + "@yandex.ru", null},
                {null, null},
        };
    }

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = new User(email, password, name);
    }

    @Test
    @DisplayName("Авторизация с некорректными данными")
    public void uncorrectedLoginTest() {
        ValidatableResponse loginUserResponse = userClient.loginUser(email, password);
        loginUserResponse.assertThat().statusCode(SC_UNAUTHORIZED).body("success", is(false));
    }
}
