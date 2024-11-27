import POJO.User;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import utils.ActionBrowser;
import utils.UserGeneration;
import yandex.praktikum.LoginPage;
import yandex.praktikum.MainPage;

import static utils.UserGeneration.deleteUser;

@RunWith(Parameterized.class)
public class RegistrationTest {
    private WebDriver driver;
    private final String browser;
    private User testUser; // Объект для хранения сгенерированного пользователя

    @Parameterized.Parameters
    public static Object[] browsers() {
        return new Object[]{"chrome", "firefox"};
    }

    public RegistrationTest(String browser) {
        this.browser = browser;
    }

    @Before
    public void setUp() {
        ActionBrowser.setUpBrowser(browser);
        driver = ActionBrowser.getDriver();
        MainPage mainPage = new MainPage(driver);
        mainPage.clickPersonalAccount();

        // Генерация уникального пользователя перед тестами
        testUser = UserGeneration.generateUserData();
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
        String incorrectPassword = "12345"; // Менее 6 символов
        loginPage.fillPasswordField(incorrectPassword);
        loginPage.clickButtonRegistration();
        loginPage.waitErrorMessageAboutPassword();
    }

    @After
    public void teardown() {
        ActionBrowser.tearDown();
        //удаляем тестового пользователя
        if (testUser != null && testUser.getToken() != null) {
            deleteUser(testUser.getToken());
        }
    }
}
