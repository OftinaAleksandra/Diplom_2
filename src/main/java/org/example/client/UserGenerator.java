package org.example.client;

import org.apache.commons.lang3.RandomStringUtils;

public class UserGenerator {
public static User getRandomUser(){
        final String email = RandomStringUtils.randomAlphabetic(3, 15)+"@yandex.ru";
        final String password = RandomStringUtils.randomAlphabetic(8, 15);
        final String name = RandomStringUtils.randomAlphabetic(3, 12);
        return new User(email, password, name);
}
}
