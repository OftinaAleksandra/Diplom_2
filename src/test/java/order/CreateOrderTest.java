package order;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.client.User;
import org.example.client.UserClient;
import org.example.client.UserGenerator;
import org.example.order.Order;
import org.example.order.OrderClient;
import org.example.order.OrderGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.is;

public class CreateOrderTest {
    private UserClient userClient;
    private User user;
    private String accessToken;
    private Order order;
    private OrderClient orderClient;


    @Before
    public void setUp() {
        userClient = new UserClient();
        user = UserGenerator.getRandomUser();
        orderClient = new OrderClient();
        order = OrderGenerator.getRandomOrder();
        accessToken = userClient.createUser(user).extract().path("accessToken");
    }

    @Test
    @DisplayName("Создание заказа с авторизацией  и с ингредиентами")
    public void createOrderWithAuthTest() {
        ValidatableResponse createOrderClient = orderClient.createOrder(accessToken, order);
        createOrderClient.assertThat().statusCode(SC_OK).body("success", is(true));
    }

    @Test
    @DisplayName("Создание заказа без авторизациии")
    public void createOrderWithoutAuthTest() {
        ValidatableResponse createOrderClient = orderClient.createOrder("", order);
        createOrderClient.assertThat().statusCode(SC_OK).body("success", is(true));
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов")
    public void createOrderWithoutIngredientsTest() {
        order = new Order();
        ValidatableResponse createOrderClient = orderClient.createOrder("", order);
        createOrderClient.assertThat().statusCode(SC_BAD_REQUEST).body("success", is(false));
    }
    @Test
    @DisplayName("Создание заказа с некорректными ингредиентами")
    public void createOrderWithUncorrectedIngredientsTest() {
        order = new Order(List.of("15344511516516251216"));
        ValidatableResponse createOrderClient = orderClient.createOrder("", order);
        createOrderClient.assertThat().statusCode(SC_INTERNAL_SERVER_ERROR);
    }

    @After
    public void cleanUp() {
        userClient.deleteUser(accessToken);
    }
}
