package client;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.client.User;
import org.example.client.UserClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.hamcrest.Matchers.is;

@RunWith(Parameterized.class)
public class UncorrectedCreateParamTest {
    private UserClient userClient;
    private User user;
    private String email;
    private String password;
    private String name;

    public UncorrectedCreateParamTest(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    @Parameterized.Parameters(name = "Создание пользователя с почтой {0}, паролем {1} и именем {3}")
    public static Object[][] getUsersData() {
        return new Object[][]{
                {RandomStringUtils.randomAlphabetic(1, 5) + "@yandex.ru", RandomStringUtils.randomAlphabetic(6, 15), null},
                {RandomStringUtils.randomAlphabetic(1, 5) + "@yandex.ru", null, RandomStringUtils.randomAlphabetic(1, 12)},
                {null,  RandomStringUtils.randomAlphabetic(6, 15), RandomStringUtils.randomAlphabetic(1, 12)},
                {null, null, null},
        };
    }

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = new User(email, password, name);
    }
    @Test
    @DisplayName("Создание пользователя без обязательных полей")
    public void uncorrectedCreateUserTest() {
        ValidatableResponse createUserResponse = userClient.createUser(user);
        createUserResponse.assertThat().statusCode(SC_FORBIDDEN).body("success", is(false));
    }

}
