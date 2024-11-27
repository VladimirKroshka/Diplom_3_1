package yandex.praktikum;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PersonalAccountPage {
    private final WebDriver driver;

    public PersonalAccountPage(WebDriver driver) {
        this.driver = driver;
    }

    // Локатор для кнопки "Профиль"
    private By profileText = By.cssSelector("a[href='/account/profile']");
    // Локатор для кнопки выход
    private By logoutButton = By.xpath("//button[contains(text(), 'Выход')]");

    public void waitForProfileAccount(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(profileText));
        Assert.assertNotNull("Элемент 'Профиль' не найден на странице", element);
    }

    public void clickLogoutButton(){
        driver.findElement(logoutButton).click();
    }
}
