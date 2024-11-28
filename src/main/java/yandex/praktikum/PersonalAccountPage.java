package yandex.praktikum;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PersonalAccountPage extends BasePage {

    // Локатор для кнопки "Профиль"
    private final By profileText = By.cssSelector("a[href='/account/profile']");
    // Локатор для кнопки "Выход"
    private final By logoutButton = By.xpath("//button[contains(text(), 'Выход')]");

    public PersonalAccountPage(WebDriver driver) {
        super(driver); // Передаем WebDriver в родительский класс
    }

    // Ожидание отображения кнопки "Профиль"
    public void waitForProfileAccount(WebDriver driver) {
        waitForElementToBeVisible(profileText, 10);
    }

    // Клик по кнопке "Выход"
    public void clickLogoutButton() {
        clickElement(logoutButton);
    }
}
