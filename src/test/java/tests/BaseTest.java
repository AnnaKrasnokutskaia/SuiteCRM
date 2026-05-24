package tests;

import io.qameta.allure.Description;
import io.qameta.allure.testng.AllureTestNg;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestContext;
import org.testng.annotations.*;

import utils.TestListener;
import step.AccountStep;
import step.ContactStep;
import step.LoginStep;

import java.time.Duration;
import java.util.HashMap;

/**
 * Базовый класс для тестов.
 * Здесь создаётся и закрывается браузер, чтобы не повторять это в каждом тесте.
 */
@Listeners({AllureTestNg.class, TestListener.class})
public class BaseTest {
    protected WebDriver driver;
    protected LoginStep loginStep;
    protected AccountStep accountStep;
    protected ContactStep contactStep;

    /**
     * Перед каждым тестом создаёт браузер.
     * Параметр browser можно передать из testng.xml; если параметра нет, запускается Chrome.
     */
    @Parameters({"browser"})
    @BeforeMethod(alwaysRun = true)
    @Description("Настройка браузера")
    public void setUp(@Optional("chrome") String browser, ITestContext iTestContext) {
        if (browser.equalsIgnoreCase("chrome")){
            ChromeOptions options = new ChromeOptions();
            HashMap<String, Object> chromePrefs = new HashMap<>();
            chromePrefs.put("credentials_enable_service", false);
            chromePrefs.put("profile.password_manager_enabled", false);
            options.setExperimentalOption("prefs", chromePrefs);
            options.addArguments("--incognito");
            options.addArguments("--disable-notifications");
            options.addArguments("--disable-popup-blocking");
            options.addArguments("--disable-infobars");
            driver = new ChromeDriver(options);
        } else if (browser.equalsIgnoreCase("firefox")){
            driver = new FirefoxDriver();
        } else {
            throw new IllegalArgumentException("Unsupported browser: " + browser);
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        iTestContext.setAttribute("driver", driver);

        loginStep = new LoginStep(driver);
        accountStep = new AccountStep();
        contactStep = new ContactStep();
    }

    /**
     * После каждого теста закрывает браузер.
     * Проверка на null нужна, чтобы не падать в tearDown, если браузер не создался.
     */
    @AfterMethod (alwaysRun = true)
    @Description("Закрытие браузера")
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
