package wrappers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Wrapper для выпадающего списка select.
 * Select ищется по видимому label строки формы.
 */
public class Select {

    private final WebDriverWait wait;
    private final String label;

    /**
     * Создаёт wrapper для select с указанным label.
     */
    public Select(WebDriver driver, String label) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.label = label;
    }

    /**
     * Выбирает option по видимому тексту.
     */
    public void select(String option) {
        WebElement select = wait.until(ExpectedConditions.elementToBeClickable(selectByLabel()));
        new org.openqa.selenium.support.ui.Select(select).selectByVisibleText(option);
    }

    /**
     * Возвращает локатор select внутри строки формы с нужным label.
     */
    private By selectByLabel() {
        return By.xpath(editFieldRowByLabel(label) + "//select[1]");
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
