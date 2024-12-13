package resources.utils;

import resources.pojo.User;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static resources.utils.RandomGenerator.generateRandomNumber;

public class UserGeneration {

    public static final String BASE_API = "https://stellarburgers.nomoreparties.site/api";
    public static final String API_REGISTER_USER = "/auth/register";
    public static final String API_DELETED_USER = "/auth/user";
    public static final String API_LOGIN_USER = "/auth/login";

    /**
     * Генерация объекта User с уникальными данными (без взаимодействия с API).
     *
     * @return Объект User с уникальными именем, email и паролем.
     */
    public static User generateUserData() {
        final String name = "Иван" + generateRandomNumber(6);
        final String email = "Test" + generateRandomNumber(6) + "@yandex.ru";
        final String password = generateRandomNumber(6);
        return new User(name, email, password);
    }

    /**
     * Создание нового пользователя с регистрацией через API.
     *
     * @return Зарегистрированный пользователь с заполненным токеном.
     */
    public static User createUniqueUser() {
        User newUser = generateUserData();

        // Отправка запроса на регистрацию пользователя
        Response response = given()
                .contentType("application/json")
                .body(newUser)
                .log().all()
                .post(BASE_API + API_REGISTER_USER);

        // Проверка ответа и извлечение токена
        response.then()
                .log().all()
                .statusCode(200)
                .body("success", equalTo(true));
        String accessToken = response.jsonPath().getString("accessToken").replace("Bearer ", "");
        newUser.setToken(accessToken);

        return newUser;
    }

    public static String loginUserForReturnAccessToken(User user) {
        Response response = given()
                .contentType("application/json")
                .log().all()
                .body(user)
                .post(BASE_API + API_LOGIN_USER);

        response.then()
                .log().all()
                .statusCode(200)
                .body("success", equalTo(true));
        // Возвращаем токен пользователя
        return response.jsonPath().getString("accessToken").replace("Bearer ", "");
    }

    /**
     * Удаление пользователя по токену.
     *
     * @param accessToken Токен пользователя для авторизации.
     */
    public static void deleteUser(String accessToken) {
        given()
                .auth().oauth2(accessToken)
                .log().all()
                .when()
                .delete(BASE_API + API_DELETED_USER)
                .then()
                .log().all()
                .statusCode(202);
    }
}
