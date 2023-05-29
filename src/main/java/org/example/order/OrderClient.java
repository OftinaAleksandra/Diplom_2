package org.example.order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.example.RestClient;
import org.example.client.User;

import static io.restassured.RestAssured.given;

public class OrderClient extends RestClient {
    private static final String CREATE_ORDER_PATH = "api/orders";
    private static final String GET_ALL_ORDERS_PATH = "api/orders/all";
    private static final String GET_USERS_ORDERS_PATH = "api/orders";
    @Step("Создание заказа")
    public ValidatableResponse createOrder(String accessToken, Order order) {
        return given()
                .spec(getBaseSpec())
                .header("authorization", accessToken)
                .body(order)
                .when()
                .post(CREATE_ORDER_PATH)
                .then();
    }
    @Step("Получение заказов пользователя")
    public ValidatableResponse getOrderCurrentUser(String accessToken, Order order) {
        return given()
                .spec(getBaseSpec())
                .header("authorization", accessToken)
                .body(order)
                .when()
                .get(GET_USERS_ORDERS_PATH)
                .then();
    }

}
