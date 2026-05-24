package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object detail-страницы созданного Contact.
 * Нужен для проверки, что после сохранения данные отображаются правильно.
 */
public class ContactPage extends BasePage {

    /**
     * Передаёт driver в BasePage.
     */
    public ContactPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Проверяет, что открылась detail-страница Contact.
     */
    public ContactPage waitForPageOpened() {
        waitForVisible(By.cssSelector("#pagecontent[data-module='Contacts'] .detail-view"));
        return this;
    }

    /**
     * Возвращает заголовок Contact.
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
     * Открывает вкладку MORE INFORMATION и ждёт, что там появилось поле Lead Source.
     */
    public ContactPage openMoreInformationTab() {
        openDetailTab("MORE INFORMATION");
        waitForVisible(By.xpath("//div[contains(@class, 'detail-view-row-item') and .//div[contains(@class, 'label') and contains(normalize-space(.), 'Lead Source')]]"));
        return this;
    }
}
