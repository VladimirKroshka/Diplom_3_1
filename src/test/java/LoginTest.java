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
import yandex.praktikum.PersonalAccountPage;
import java.io.IOException;

import static resources.utils.UserGeneration.deleteUser;

public class LoginTest {
    private WebDriver driver;
    private User testUser; // Сгенерированный пользователь

    MainPage mainPage;
    LoginPage loginPage;
    PersonalAccountPage personalAccountPage;

    @Before
    public void setUp() throws IOException {
        // Загрузка конфигурации браузера из файла или переменной окружения
        ActionBrowser.loadBrowserConfig();
        String browser = ActionBrowser.browser; // Присваиваем значение browser из ActionBrowser

        // Настройка браузера
        ActionBrowser.setUpBrowser(browser);
        driver = ActionBrowser.getDriver();

        // Инициализация страниц
        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
        personalAccountPage = new PersonalAccountPage(driver);

        // Генерация данных пользователя для тестов
        testUser = UserGeneration.createUniqueUser();
    }

    @After
    public void teardown() {
        // Завершение работы с драйвером и удаление пользователя
        ActionBrowser.tearDown();
        if (testUser != null && testUser.getToken() != null) {
            deleteUser(testUser.getToken());
        }
    }

    @Test
    @DisplayName("Вход пользователя")
    @io.qameta.allure.Description("Вход по кнопке «Войти в аккаунт» на главной")
    public void testLoginUsingButtonMainPage() {
        loginUsingButtonMainPage();
    }

    @Test
    @DisplayName("Вход пользователя")
    @io.qameta.allure.Description("Вход через кнопку «Личный кабинет»")
    public void testLoginUsingButtonPersonalAccount() {
        loginUsingButtonPersonalAccount();
    }

    @Test
    @DisplayName("Вход пользователя")
    @io.qameta.allure.Description("Вход через кнопку в форме регистрации")
    public void testloginUsingFormRegistration() {
        loginUsingFormRegistration();
    }

    @Test
    @DisplayName("Вход пользователя")
    @io.qameta.allure.Description("Вход через кнопку в форме восстановления пароля")
    public void testloginUsingRecoveryPassword() {
        loginUsingRecoveryPassword();
    }

    @Test
    @DisplayName("Выход из аккаунта")
    @io.qameta.allure.Description("Проверка выхода по кнопке «Выйти» в личном кабинете.")
    public void testLogoutFromPersonalAccount() {
        logoutFromPersonalAccount();
    }

    @Step("Вход по кнопке «Войти в аккаунт» на главной")
    private void loginUsingButtonMainPage() {
        mainPage.clickLoginButton();
        loginUser();
        testSuccesfullLogin();
    }

    @Step("Вход через кнопку «Личный кабинет»")
    private void loginUsingButtonPersonalAccount() {
        mainPage.clickPersonalAccount();
        loginUser();
        testSuccesfullLogin();
    }

    @Step("Вход через кнопку в форме регистрации")
    private void loginUsingFormRegistration() {
        mainPage.clickPersonalAccount();
        loginPage.clickRegistration();
        loginPage.clickLinkLogin();
        loginUser();
        testSuccesfullLogin();
    }

    @Step("Вход через кнопку в форме восстановления пароля")
    private void loginUsingRecoveryPassword() {
        mainPage.clickPersonalAccount();
        loginPage.clickLinkRecover();
        loginPage.clickLinkLogin();
        loginUser();
        testSuccesfullLogin();
    }

    @Step("Проверка выхода по кнопке «Выйти» в личном кабинете.")
    private void logoutFromPersonalAccount() {
        mainPage.clickLoginButton();
        loginUser();
        testSuccesfullLogin();
        personalAccountPage.clickLogoutButton();
        // Дожидаемся линка "Зарегистрироваться"
        loginPage.waitForLinkRegistration();
    }

    private void loginUser() {
        // Используем данные из сгенерированного пользователя
        loginPage.fillEmailField(testUser.getEmail());
        loginPage.fillPasswordField(testUser.getPassword());
        loginPage.clickLoginButton();
    }

    // Метод для проверки успешного входа
    private void testSuccesfullLogin() {
        mainPage.waitForPersonalAccountButton(driver);
        mainPage.clickPersonalAccount();
        personalAccountPage.waitForProfileAccount(driver);
    }
}
