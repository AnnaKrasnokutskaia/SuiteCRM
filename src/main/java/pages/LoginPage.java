package pages;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object страницы логина SuiteCRM.
 */
@Log4j2
public class LoginPage extends BasePage {

    private final By userNameInput = By.id("user_name");
    private final By passwordInput = By.id("username_password");
    private final By loginButton = By.name("Login");

    /**
     * Передаёт driver в BasePage.
     */
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Открывает страницу логина.
     * Возвращает текущую страницу, чтобы можно было продолжить цепочку вызовов.
     */
    @Step("Открыть страницу логина")
    public LoginPage open() {
        driver.get(BASE_URL + "/index.php?module=Users&action=Login");
        return waitForPageOpened();
    }

    /**
     * Loadable Page: проверяет, что страница логина действительно открылась.
     */
    public LoginPage waitForPageOpened() {
        waitForVisible(userNameInput);
        return this;
    }

    /**
     * Заполняет логин и пароль, нажимает Login и возвращает главную страницу после авторизации.
     */
    @Step("Авторизоваться пользователем {username}")
    public MainPage loginAs(String username, String password) {
        log.info("Login with credentials '{}', '{}'", username, password);
        waitForVisible(userNameInput).clear();
        waitForVisible(userNameInput).sendKeys(username);
        waitForVisible(passwordInput).clear();
        waitForVisible(passwordInput).sendKeys(password);
        click(loginButton);
        return new MainPage(driver).waitForPageOpened();
    }
}
