package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Базовый класс для всех Page Object.
 * Здесь лежит общая логика, которая нужна разным страницам:
 * адрес сайта, driver, явные ожидания и методы для чтения detail-страницы.
 */
public abstract class BasePage {

    protected static final String BASE_URL = "https://demo.suiteondemand.com";
    protected final WebDriver driver;
    protected final WebDriverWait wait;

    /**
     * Сохраняет WebDriver и создаёт явное ожидание на 15 секунд.
     * Все дочерние страницы получают driver через super(driver).
     */
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    /**
     * Ждёт, пока элемент появится на странице и станет видимым.
     * Метод нужен, чтобы не искать элементы раньше, чем страница успела загрузиться.
     */
    protected WebElement waitForVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Ждёт, пока элемент станет кликабельным.
     * Используется для кнопок, вкладок и других элементов, по которым надо нажать.
     */
    protected WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Кликает по элементу только после ожидания кликабельности.
     * Это короче и безопаснее, чем каждый раз писать wait.until(...).click().
     */
    protected void click(By locator) {
        waitForClickable(locator).click();
    }

    /**
     * Открывает вкладку на detail-странице по её видимому названию.
     * Например, MORE INFORMATION.
     */
    protected void openDetailTab(String tabName) {
        waitForClickable(By.xpath(
                "//ul[contains(@class, 'nav-tabs')]" +
                        "//li[contains(@class, 'hidden-xs')]" +
                        "/a[normalize-space(.)='" + tabName + "']"
        )).click();
    }

    /**
     * Возвращает заголовок открытой страницы сущности.
     * Например, имя созданного Account или Contact из блока module-title-text.
     */
    protected String getModuleTitle() {
        return waitForVisible(By.cssSelector(".module-title-text")).getText().trim();
    }

    /**
     * Возвращает значение поля на detail-странице по видимому названию label.
     * Сначала метод пробует взять обычный видимый текст.
     * Если текста нет, ищет скрытые поля SuiteCRM с классом sugar_field
     * и берёт значение из атрибута value.
     */
    protected String getDetailFieldValue(String label) {
        WebElement field = waitForVisible(By.xpath(detailFieldByLabel(label)));

        String text = field.getText().trim();
        if (!text.isEmpty()) {
            return text;
        }

        List<WebElement> hiddenValues = field.findElements(By.className("sugar_field"));
        for (WebElement hiddenValue : hiddenValues) {
            String value = hiddenValue.getAttribute("value");
            if (value != null && !value.trim().isEmpty()) {
                return value.trim();
            }
        }

        return "";
    }

    /**
     * Собирает XPath для значения поля на detail-странице.
     * Ищем строку detail-view-row-item, внутри которой label содержит нужный текст,
     * а потом берём блок detail-view-field со значением.
     */
    private String detailFieldByLabel(String label) {
        return "//div[contains(@class, 'detail-view-row-item')" +
                " and .//div[contains(@class, 'label')" +
                " and contains(normalize-space(.), '" + label + "')]]" +
                "//*[contains(@class, 'detail-view-field')]";
    }
}
