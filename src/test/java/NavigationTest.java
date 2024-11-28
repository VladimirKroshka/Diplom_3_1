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

public class NavigationTest {
    private WebDriver driver; // WebDriver
    private User testUser; // Сгенерированный пользователь

    private MainPage mainPage;
    private LoginPage loginPage;
    private PersonalAccountPage personalAccountPage;
    private String browser;

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
    }

    @After
    public void teardown() {
        if (testUser != null && testUser.getToken() != null) {
            deleteUser(testUser.getToken());
        }
        ActionBrowser.tearDown();
    }

    @Test
    @DisplayName("Переход в личный кабинет (авторизованный пользователь)")
    @io.qameta.allure.Description("Тест для авторизованного пользователя")
    public void testFollowPersonalAccountForAuthUser() {
        testUser = UserGeneration.createUniqueUser();
        loginUser();
        navigateToPersonalAccount(true);
    }

    @Test
    @DisplayName("Переход в личный кабинет (неавторизованный пользователь)")
    @io.qameta.allure.Description("Тест для неавторизованного пользователя")
    public void testFollowPersonalAccountForNotAuthUser() {
        navigateToPersonalAccount(false);
    }

    @Test
    @DisplayName("Переход на главную страницу через «Конструктор»")
    @io.qameta.allure.Description("Тест перехода по кнопке «Конструктор»")
    public void testFollowToMainPageByConstructor() {
        navigateToMainPage("constructor");
    }

    @Test
    @DisplayName("Переход на главную страницу через логотип")
    @io.qameta.allure.Description("Тест перехода по логотипу Stellar Burgers")
    public void testFollowToMainPageByLogo() {
        navigateToMainPage("logo");
    }

    // Разделение теста testConstructorTabs на отдельные тесты для каждого таба
    @Test
    @DisplayName("Переход в раздел «Булки»")
    @io.qameta.allure.Description("Тест перехода в раздел «Булки»")
    public void testTabBuns() {
        verifyTabNavigation("Булки");
    }

    @Test
    @DisplayName("Переход в раздел «Соусы»")
    @io.qameta.allure.Description("Тест перехода в раздел «Соусы»")
    public void testTabSauces() {
        verifyTabNavigation("Соусы");
    }

    @Test
    @DisplayName("Переход в раздел «Начинки»")
    @io.qameta.allure.Description("Тест перехода в раздел «Начинки»")
    public void testTabFillings() {
        verifyTabNavigation("Начинки");
    }

    // Универсальные методы

    @Step("Переход в личный кабинет")
    private void navigateToPersonalAccount(boolean isAuthorized) {
        mainPage.clickPersonalAccount();
        if (isAuthorized) {
            personalAccountPage.waitForProfileAccount(driver);
        } else {
            loginPage.waitForLinkRegistration();
        }
    }

    @Step("Переход на главную страницу через {type}")
    private void navigateToMainPage(String type) {
        mainPage.clickPersonalAccount();
        if (type.equals("constructor")) {
            mainPage.clickConstructorButton();
        } else if (type.equals("logo")) {
            mainPage.clickLogo();
        }
        mainPage.waitForBannerBuildBurger(driver);
    }

    @Step("Проверка перехода в раздел {tabName}")
    private void verifyTabNavigation(String tabName) {
        switch (tabName) {
            case "Булки":
                mainPage.clickTabBuns();
                mainPage.verifyActiveTabBuns();
                break;
            case "Соусы":
                mainPage.clickTabSauces();
                mainPage.verifyActiveTabSauces();
                break;
            case "Начинки":
                mainPage.clickTabFillings();
                mainPage.verifyActiveTabFillings();
                break;
        }
    }

    private void loginUser() {
        mainPage.clickPersonalAccount();
        loginPage.fillEmailField(testUser.getEmail());
        loginPage.fillPasswordField(testUser.getPassword());
        loginPage.clickLoginButton();
    }
}
