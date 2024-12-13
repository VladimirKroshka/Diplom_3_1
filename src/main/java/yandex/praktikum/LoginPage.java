package yandex.praktikum;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    // Локаторы кнопок
    private final By loginButton = By.xpath("//button[text()='Войти']");
    private final By buttonRegistration = By.xpath("//button[text()='Зарегистрироваться']");
    private final By buttonMainPage = By.className("AppHeader_header__logo__2D0X2");

    // Локаторы гиперссылок
    private final By linkRegistration = By.linkText("Зарегистрироваться");
    private final By linkLogin = By.xpath("//a[text()='Войти' and @href='/login']");
    private final By linkRecover = By.xpath("//a[text()='Восстановить пароль' and @href='/forgot-password']");

    // Локаторы полей регистрации
    private final By nameField = By.xpath("//label[text()='Имя']/following-sibling::input");
    private final By emailField = By.xpath("//label[text()='Email']/following-sibling::input");
    private final By passwordField = By.xpath("//label[text()='Пароль']/following-sibling::input");
    private final By errorPasswordMessageLocator = By.xpath("//p[contains(@class, 'input__error') and text()='Некорректный пароль']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    // Методы ожидания
    public void waitForLoginButton() {
        waitForElementToBeVisible(loginButton, 10);
    }

    public void waitErrorMessageAboutPassword() {
        waitForElementToBeVisible(errorPasswordMessageLocator, 10);
    }

    public void waitForLinkRegistration() {
        waitForElementToBeVisible(linkRegistration, 10);
    }

    // Методы действий
    public void clickRegistration() {
        clickElement(linkRegistration);
    }

    public void fillNameField(String name) {
        sendKeysToElement(nameField, name);
    }

    public void fillEmailField(String email) {
        sendKeysToElement(emailField, email);
    }

    public void fillPasswordField(String password) {
        sendKeysToElement(passwordField, password);
    }

    public void clickButtonRegistration() {
        clickElement(buttonRegistration);
    }

    public void clickLoginButton() {
        clickElement(loginButton);
    }

    public void clickLinkLogin() {
        clickElement(linkLogin);
    }

    public void clickLinkRecover() {
        clickElement(linkRecover);
    }

    public void clickButtonMainPage() {
        clickElement(buttonMainPage);
    }
}
