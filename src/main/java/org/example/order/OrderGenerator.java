package org.example.order;

import java.util.List;

public class OrderGenerator {
    public static Order getRandomOrder() {
        return new Order(List.of("61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f", "61c0c5a71d1f82001bdaaa70", "61c0c5a71d1f82001bdaaa72"));
    }
}
