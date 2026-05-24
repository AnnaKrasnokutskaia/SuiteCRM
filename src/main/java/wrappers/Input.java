package wrappers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Wrapper для обычного текстового input.
 * Поле ищется по видимому названию label на странице.
 */
public class Input {

    private final WebDriverWait wait;
    private final String label;

    /**
     * Создаёт wrapper для input с указанным label.
     */
    public Input(WebDriver driver, String label) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.label = label;
    }

    /**
     * Находит input, очищает его и вводит текст.
     */
    public void write(String text) {
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(inputByLabel()));
        input.clear();
        input.sendKeys(text);
    }

    /**
     * Возвращает локатор input внутри строки формы с нужным label.
     */
    private By inputByLabel() {
        return By.xpath(editFieldRowByLabel(label) +
                "//input[not(@type='hidden') and not(@type='button') and not(@type='submit')][1]");
    }

    /**
     * Собирает XPath строки формы edit-view-row-item по видимому названию поля.
     * Используем contains, потому label на странице может быть с двоеточием или звёздочкой.
     */
    private String editFieldRowByLabel(String label) {
        return "//form[@id='EditView']" +
                "//div[contains(@class, 'edit-view-row-item')" +
                " and .//div[contains(@class, 'label')" +
                " and contains(normalize-space(.), '" + label + "')]]";
    }
}
