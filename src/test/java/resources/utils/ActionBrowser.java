package resources.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static resources.Endpoints.BASE_URL;

public class ActionBrowser {
    private static WebDriver driver;
    public static String browser;

    public static void setUpBrowser(String browser) {
        if ("chrome".equalsIgnoreCase(browser)) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--no-sandbox", "--headless", "--disable-dev-shm-usage");
            //options.addArguments("--no-sandbox", "--disable-dev-shm-usage");
            WebDriverManager.chromedriver().clearDriverCache().setup();
            WebDriverManager.chromedriver().clearResolutionCache().setup();
            driver = new ChromeDriver(options);
        } else if ("firefox".equalsIgnoreCase(browser)) {
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("--no-sandbox", "--headless", "--disable-dev-shm-usage");
            driver = new FirefoxDriver(options);
        } else {
            throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
        driver.get(BASE_URL);
    }

    public static WebDriver getDriver() {
        if (driver == null) {
            throw new IllegalStateException("Driver is not initialized. Call setUpBrowser() first.");
        }
        return driver;
    }

    public static void loadBrowserConfig() throws IOException {
        // Пробуем получить браузер из переменной окружения
        String browserFromEnv = System.getenv("BROWSER");
        if (browserFromEnv != null) {
            browser = browserFromEnv;
        } else {
            // Если переменная окружения не указана, загружаем конфигурацию из файла .properties
            Properties properties = new Properties();
            try (FileInputStream fileInputStream = new FileInputStream("src/test/java/resources/config/config.properties")) {
                properties.load(fileInputStream);
                //и если тут не удалось найти, то берем по умолчанию chrome
                browser = properties.getProperty("browser", "chrome");
            }
        }
    }

    public static void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
