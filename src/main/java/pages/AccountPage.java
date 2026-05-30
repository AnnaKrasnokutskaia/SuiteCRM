package pages;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object detail-страницы созданного Account.
 * Нужен для проверки, что после сохранения данные отображаются правильно.
 */
public class AccountPage extends BasePage {

    /**
     * Передаёт driver в BasePage.
     */
    public AccountPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Проверяет, что открылась detail-страница Account.
     */
    public AccountPage waitForPageOpened() {
        waitForVisible(By.cssSelector("#pagecontent[data-module='Accounts'] .detail-view"));
        return this;
    }

    /**
     * Возвращает заголовок Account.
     */
    public String getTitle() {
        return getModuleTitle();
    }

    /**
     * Возвращает значение поля на detail-странице по названию label.
     */
    public String getFieldValue(String label) {
        return getDetailFieldValue(label);
    }

    /**
     * Открывает вкладку MORE INFORMATION и ждёт, что там появилось поле Type.
     */
    public AccountPage openMoreInformationTab() {
        openDetailTab("MORE INFORMATION");
        waitForVisible(By.xpath("//div[contains(@class, 'detail-view-row-item') and .//div[contains(@class, 'label') and contains(normalize-space(.), 'Type')]]"));
        return this;
    }
}
