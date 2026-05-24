package step;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebDriver;
import pages.LoginPage;
import pages.MainPage;

/**
 * Сценарный шаг авторизации.
 * Внутри использует цепочку Page Object, а наружу возвращает страницу,
 * с которой тест может продолжить сценарий.
 */
@RequiredArgsConstructor
public class LoginStep {

    private final WebDriver driver;

    /**
     * Открывает страницу логина, вводит учётные данные и возвращает главную страницу.
     */
    public MainPage login(String username, String password) {
        return new LoginPage(driver)
                .open()
                .loginAs(username, password);
    }
}
