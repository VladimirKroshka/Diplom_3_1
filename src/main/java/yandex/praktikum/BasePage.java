package yandex.praktikum;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

import static org.junit.Assert.assertEquals;

public class BasePage {
    protected final WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    // Ожидание видимости элемента
    public WebElement waitForElementToBeVisible(By locator, long timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }


    // Клик по элементу
    public void clickElement(By locator) {
        WebElement element = waitForElementToBeClickable(locator, 10);
        element.click();
    }

    // Получение текста элемента
    public String getTextOfElement(By locator) {
        WebElement element = waitForElementToBeVisible(locator, 10);
        return element.getText();
    }

    // Универсальное ожидание кликабельности элемента
    public WebElement waitForElementToBeClickable(By locator, long timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    // Ввод текста в элемент
    public void sendKeysToElement(By locator, String text) {
        WebElement element = waitForElementToBeClickable(locator, 10);
        element.clear(); // Очистка поля перед вводом
        element.sendKeys(text); // Ввод текста
    }

    // Проверка текста
    public void assertTextEquals(String message, String expected, String actual) {
        assertEquals(message, expected, actual);
    }

    // Прокрутка к элементу
    public void scrollToElement(By locator) {
        WebElement element = driver.findElement(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    // Универсальный метод для перемещения к элементу и клика
    void moveToElementAndClick(WebElement element) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click().perform();
    }
}
