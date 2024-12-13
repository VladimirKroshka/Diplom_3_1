import resources.pojo.User;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import resources.utils.ActionBrowser;
import resources.utils.UserGeneration;
import yandex.praktikum.LoginPage;
import yandex.praktikum.MainPage;

import java.io.IOException;

import static resources.utils.UserGeneration.deleteUser;
import static resources.utils.RandomGenerator.generateRandomNumber;

public class RegistrationTest {
    private WebDriver driver;
    private User testUser; // Объект для хранения сгенерированного пользователя


    @Before
    public void setUp() throws IOException {
        // Загрузка конфигурации браузера из файла или переменной окружения
        ActionBrowser.loadBrowserConfig();
        String browser = ActionBrowser.browser; // Присваиваем значение browser из ActionBrowser

        // Настройка браузера
        ActionBrowser.setUpBrowser(browser);
        driver = ActionBrowser.getDriver();

        // Генерация уникального пользователя перед тестами
        testUser = UserGeneration.generateUserData();

        //Переходим на страницу регистрации
        MainPage mainPage = new MainPage(driver);
        mainPage.clickPersonalAccount();
    }

    @After
    public void teardown() {
        ActionBrowser.tearDown();
        //удаляем тестового пользователя
        if (testUser != null && testUser.getToken() != null) {
            deleteUser(testUser.getToken());
        }
    }

    @Test
    @DisplayName("Регистрация пользователя")
    @io.qameta.allure.Description("Успешная регистрация")
    public void testSuccessfulRegistration() {
        successfulRegistration();
    }

    @Test
    @DisplayName("Регистрация пользователя")
    @io.qameta.allure.Description("Неуспешная регистрация. Неверная длина пароля")
    public void testRegistrationWithIncorrectPass() {
        registrationWithIncorrectPass();
    }

    @Step("Успешная регистрация")
    public void successfulRegistration() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.waitForLinkRegistration();
        loginPage.clickRegistration();
        loginPage.fillNameField(testUser.getName());
        loginPage.fillEmailField(testUser.getEmail());
        loginPage.fillPasswordField(testUser.getPassword());
        loginPage.clickButtonRegistration();
        loginPage.waitForLoginButton();

        //получаем токен, чтобы его потом удалить
        String accessToken = UserGeneration.loginUserForReturnAccessToken(testUser);
        testUser.setToken(accessToken);
    }

    @Step("Неуспешная регистрация. Неверная длина пароля")
    public void registrationWithIncorrectPass() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.waitForLinkRegistration();
        loginPage.clickRegistration();
        loginPage.fillNameField(testUser.getName());
        loginPage.fillEmailField(testUser.getEmail());
        // Указываем некорректный пароль для теста
        String incorrectPassword = generateRandomNumber(5); // Менее 6 символов
        loginPage.fillPasswordField(incorrectPassword);
        loginPage.clickButtonRegistration();
        loginPage.waitErrorMessageAboutPassword();
    }
}
