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
import yandex.praktikum.PersonalAccountPage;

import static utils.UserGeneration.deleteUser;

@RunWith(Parameterized.class)
public class NavigationTest {
    private WebDriver driver; // WebDriver
    private final String browser;
    private User testUser; // Сгенерированный пользователь

    private MainPage mainPage;
    private LoginPage loginPage;
    private PersonalAccountPage personalAccountPage;

    // Параметры браузеров
    @Parameterized.Parameters
    public static Object[] browsers() {
        return new Object[]{"chrome", "firefox"};
    }

    // Конструктор с параметром браузера
    public NavigationTest(String browser) {
        this.browser = browser;
    }

    @Before
    public void setUp() {
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

    @Test
    @DisplayName("Проверка переходов в разделе конструктора")
    @io.qameta.allure.Description("Тест переходов в разделы «Булки», «Соусы», «Начинки»")
    public void testConstructorTabs() {
        verifyTabNavigation("Булки");
        verifyTabNavigation("Соусы");
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
        mainPage.waitBannerBuildBurger(driver);
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
                mainPage.verifyActivetabSauces();
                break;
            case "Начинки":
                mainPage.clickTabFillings();
                mainPage.verifyActivetabFillings();
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
