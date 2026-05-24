package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object главной страницы после успешного логина.
 * Сейчас используется как точка, из которой открываются формы создания сущностей.
 */
public class MainPage extends BasePage {

    /**
     * Передаёт driver в BasePage.
     */
    public MainPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Проверяет, что после логина открылась Home-страница.
     * Ориентируемся на pagecontent с data-module='Home', потому что этот блок есть на странице после входа.
     */
    public MainPage waitForPageOpened() {
        waitForVisible(By.cssSelector("#pagecontent[data-module='Home']"));
        return this;
    }

    /**
     * Открывает форму создания Account прямой ссылкой.
     * Возвращает AddAccountPage, потому что дальше тест будет заполнять именно эту форму.
     */
    @Step("Открыть форму создания Account")
    public AddAccountPage openAddAccountPage() {
        driver.get(BASE_URL + "/index.php?module=Accounts&action=EditView&return_module=Accounts&return_action=DetailView");
        return new AddAccountPage(driver).waitForPageOpened();
    }

    /**
     * Открывает форму создания Contact прямой ссылкой.
     * Возвращает AddContactPage, потому что дальше тест будет заполнять именно эту форму.
     */
    @Step("Открыть форму создания Contact")
    public AddContactPage openAddContactPage() {
        driver.get(BASE_URL + "/index.php?module=Contacts&action=EditView&return_module=Contacts&return_action=DetailView");
        return new AddContactPage(driver).waitForPageOpened();
    }
}
