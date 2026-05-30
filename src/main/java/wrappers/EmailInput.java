package wrappers;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Wrapper для поля Email Address.
 * В SuiteCRM email сделан отдельным виджетом, поэтому обычный Input для него не подходит.
 */
@Log4j2
public class EmailInput {

    private final WebDriverWait wait;
    private final String label;

    /**
     * Создаёт wrapper для email-поля с указанным label.
     */
    public EmailInput(WebDriver driver, String label) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.label = label;
    }

    /**
     * Находит активный email input, очищает его и вводит email.
     */
    public void write(String email) {
        log.info("Writing '{}' in to email input '{}'", email, label);
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(emailInputByLabel()));
        input.clear();
        input.sendKeys(email);
    }

    /**
     * Возвращает локатор email input внутри строки формы с label Email Address.
     * Скрытый template исключается, потому что виджет хранит шаблон email-строки в DOM.
     */
    private By emailInputByLabel() {
        return By.xpath(editFieldRowByLabel(label) +
                "//input[@type='email' and not(ancestor::div[contains(@class, 'template')])][1]");
    }

    /**
     * Собирает XPath строки формы edit-view-row-item по видимому названию поля.
     */
    private String editFieldRowByLabel(String label) {
        return "//form[@id='EditView']" +
                "//div[contains(@class, 'edit-view-row-item')" +
                " and .//div[contains(@class, 'label')" +
                " and contains(normalize-space(.), '" + label + "')]]";
    }
}
