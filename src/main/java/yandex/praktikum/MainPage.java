package yandex.praktikum;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MainPage extends BasePage {

    // Локаторы кнопок и элементов
    private final By buttonPersonalAccount = By.xpath("//p[text()='Личный Кабинет']");
    private final By loginButton = By.cssSelector("button.button_button__33qZ0.button_button_type_primary__1O7Bx.button_button_size_large__G21Vg");
    private final By logoWebsite = By.xpath("//div[contains(@class, 'logo')]/a[@href='/']");
    private final By constructorButton = By.xpath("//p[contains(@class, 'AppHeader_header__linkText__3q_va') and text()='Конструктор']");
    private final By bannerBuildBurger = By.xpath("//h1[contains(@class, 'text_type_main-large') and text()='Соберите бургер']");

    // Локаторы вкладок
    private final By tabBuns = By.xpath("//span[text()='Булки']");
    private final By tabSauces = By.xpath("//span[text()='Соусы']");
    private final By tabFillings = By.xpath("//span[text()='Начинки']");
    private final By activeTab = By.xpath("//div[contains(@class, 'tab_tab_type_current__2BEPc')]/span");

    public MainPage(WebDriver driver) {
        super(driver);
    }

    // Методы взаимодействия с элементами
    public void waitForPersonalAccountButton(WebDriver driver) {
        waitForElementToBeVisible(buttonPersonalAccount, 10);
    }

    public void clickPersonalAccount() {
        clickElement(buttonPersonalAccount);
    }

    public void clickLoginButton() {

        clickElement(loginButton);
    }

    public void clickLogo() {
        clickElement(logoWebsite);
    }

    public void clickConstructorButton() {
        clickElement(constructorButton);
    }

    public void waitForBannerBuildBurger(WebDriver driver) {
        waitForElementToBeVisible(bannerBuildBurger, 10);
    }
    //хитрые табы, надо сначала дождаться ее появления, а потом имитировать движение к элементу и клик
    public void clickTabBuns() {
        WebElement element = waitForElementToBeVisible(tabBuns, 10);
        moveToElementAndClick(element);
    }

    public void clickTabSauces() {
        WebElement element = waitForElementToBeVisible(tabSauces, 10);
        moveToElementAndClick(element);
    }

    public void clickTabFillings() {
        WebElement element = waitForElementToBeVisible(tabFillings, 10);
        moveToElementAndClick(element);
    }

    public void verifyActiveTabBuns() {
        verifyActiveTab("Булки");
    }

    public void verifyActiveTabSauces() {
        verifyActiveTab("Соусы");
    }

    public void verifyActiveTabFillings() {
        verifyActiveTab("Начинки");
    }

    public String getActiveTabName() {
        return getTextOfElement(activeTab);
    }

    // Универсальные методы
    private void clickTab(By tabLocator) {
        clickElement(tabLocator);
    }

    private void verifyActiveTab(String expectedTabName) {
        String activeTabName = getActiveTabName();
        assertTextEquals("Ожидается активная вкладка: " + expectedTabName, expectedTabName, activeTabName);
    }
}
