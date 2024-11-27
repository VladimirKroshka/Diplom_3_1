package yandex.praktikum;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.Assert.assertEquals;

// Класс главной страницы
public class MainPage {
    private final WebDriver driver;

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    // Локаторы
    public By buttonPersonalAccount = By.xpath("//p[text()='Личный Кабинет']");
    private final By loginButton = By.cssSelector("button.button_button__33qZ0.button_button_type_primary__1O7Bx.button_button_size_large__G21Vg");
    private final By logoWebsite = By.xpath("//div[contains(@class, 'logo')]/a[@href='/']");
    private final By constructorButton = By.xpath("//p[contains(@class, 'AppHeader_header__linkText__3q_va') and text()='Конструктор']");
    private final By bannerBuildBurger = By.xpath("//h1[contains(@class, 'text_type_main-large') and text()='Соберите бургер']");

    // Селекторы вкладок
    private final By tabBuns = By.xpath("//span[text()='Булки']");
    private final By tabSauces = By.xpath("//span[text()='Соусы']");
    private final By tabFillings = By.xpath("//span[text()='Начинки']");

    // Селектор активной вкладки
    private final By activeTab = By.xpath("//div[contains(@class, 'tab_tab_type_current__2BEPc')]/span");

    // Ожидание видимости элемента "Личный кабинет"
    public void waitForElementPersonalAccountToBeVisible(WebDriver driver) {
        long timeout = 10L; // Время ожидания в секундах
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        wait.until(ExpectedConditions.visibilityOfElementLocated(buttonPersonalAccount));
    }

    // Клик по кнопке "Личный кабинет"
    public void clickPersonalAccount() {
        driver.findElement(buttonPersonalAccount).click();
    }

    // Клик по кнопке "Войти"
    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    // Клик по логотипу сайта
    public void clickLogo() {
        driver.findElement(logoWebsite).click();
    }

    // Клик по кнопке "Конструктор"
    public void clickConstructorButton() {
        driver.findElement(constructorButton).click();
    }

    // Ожидание отображения баннера "Соберите бургер"
    public void waitBannerBuildBurger(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(bannerBuildBurger));
    }

    // Клик по вкладке "Булки"
    public void clickTabBuns() {
        WebElement element = waitForElement(tabBuns, 10);
        moveToElementAndClick(element);
    }

    // Клик по вкладке "Соусы"
    public void clickTabSauces() {
        WebElement element = waitForElement(tabSauces, 10);
        moveToElementAndClick(element);
    }

    // Клик по вкладке "Начинки"
    public void clickTabFillings() {
        WebElement element = waitForElement(tabFillings, 10);
        moveToElementAndClick(element);
    }

    // Проверка активной вкладки "Булки"
    public void verifyActiveTabBuns() {
        verifyActiveTab("Булки");
    }

    // Проверка активной вкладки "Соусы"
    public void verifyActivetabSauces() {
        verifyActiveTab("Соусы");
    }

    // Проверка активной вкладки "Начинки"
    public void verifyActivetabFillings() {
        verifyActiveTab("Начинки");
    }

    // Универсальный метод проверки активной вкладки
    private void verifyActiveTab(String expectedTabName) {
        String activeTabName = getActiveTabName();
        assertEquals("Ожидается, что активной будет вкладка: " + expectedTabName, expectedTabName, activeTabName);
    }

    // Получить текст активной вкладки
    public String getActiveTabName() {
        WebElement element = waitForElement(activeTab, 10);
        return element.getText();
    }

    // Универсальный метод ожидания элемента
    private WebElement waitForElement(By locator, long timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    // Универсальный метод для перемещения к элементу и клика
    private void moveToElementAndClick(WebElement element) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click().perform();
    }
}
