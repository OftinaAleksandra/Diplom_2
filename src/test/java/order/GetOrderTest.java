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

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.is;

public class GetOrderTest {
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
        @DisplayName("Получение заказов аторизованным юзером")
        public void getOrderWithAuthTest() {
            ValidatableResponse createOrderClient = orderClient.getOrderCurrentUser(accessToken, order);
            createOrderClient.assertThat().statusCode(SC_OK).body("success", is(true));
        }

        @Test
        @DisplayName("Получение заказов не аторизованным юзером")
        public void getOrderWithoutAuthTest() {
            ValidatableResponse createOrderClient = orderClient.getOrderCurrentUser("", order);
            createOrderClient.assertThat().statusCode(SC_UNAUTHORIZED).body("success", is(false));
        }

        @After
        public void cleanUp() {
            userClient.deleteUser(accessToken);
        }
    }


