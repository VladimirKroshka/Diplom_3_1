package yandex.praktikum;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private final WebDriver driver;

    //Локаторы кнопок
    private By loginButton = By.xpath("//button[text()='Войти']");
    private By buttonRegistration = By.xpath("//button[text()='Зарегистрироваться']");
    private By buttonMainPage = By.className("AppHeader_header__logo__2D0X2");

    //Локаторы гиперссылок
    private By linkRegistration = By.linkText("Зарегистрироваться");
    private By linkLogin = By.xpath("//a[text()='Войти' and @href='/login']");
    private By linkRecover = By.xpath("//a[text()='Восстановить пароль' and @href='/forgot-password']");

    //Локаторы полей регистрации
    private By nameField = By.xpath("//label[text()='Имя']/following-sibling::input");
    private By emailField = By.xpath("//label[text()='Email']/following-sibling::input");
    private By passwordField = By.xpath("//label[text()='Пароль']/following-sibling::input");
    private By errorPasswordMessageLocator = By.xpath("//p[contains(@class, 'input__error') and text()='Некорректный пароль']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void waitForElementToBeVisible(WebDriver driver, By locator) {
        long timeout = 10L; // Время ожидания в секундах
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout)); // Передаём Duration
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator)); // Условие ожидания
    }

    //метод для ожидания появление ошибки по локатору
    public void waitErrorMessage(WebDriver driver, By errorMessageLocator) {
        long timeout = 10L;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout)); // Передаём Duration в конструктор
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessageLocator));
        Assert.assertNotNull("Ошибка не отображается", errorMessage);
    }
    //Метод ожидания появление кнопки Войти
    public void waitForLoginButton() {
        long timeout = 10L;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout)); // Передаём Duration в конструктор
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(loginButton));
        Assert.assertNotNull("Кнопка 'Войти' не появилась на странице", loginButton);
    }


    public void waitErrorMessageAboutPassword() {
        waitErrorMessage(driver, errorPasswordMessageLocator);
    }

    public void waitForLinkRegistration() {
        waitForElementToBeVisible(driver, linkRegistration);
    }

    public void clickRegistration() {
        driver.findElement(linkRegistration).click();
    }

    public void fillNameField(String Name) {
        driver.findElement(nameField).sendKeys(Name);
    }

    public void fillEmailField(String Email) {
        driver.findElement(emailField).sendKeys(Email);
    }

    public void fillPasswordField(String Password) {
        driver.findElement(passwordField).sendKeys(Password);
    }

    public void clickButtonRegistration() {
        driver.findElement(buttonRegistration).click();
    }

    public void clickLoginButton(){
        driver.findElement(loginButton).click();
    }

    public void clickLinkLogin(){
        driver.findElement(linkLogin).click();
    }

    public void clickLinkRecover(){
        driver.findElement(linkRecover).click();
    }

    public void clickButtonManePage(){
        driver.findElement(buttonMainPage).click();
    }

}
