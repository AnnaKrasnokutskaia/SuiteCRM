package wrappers;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Wrapper для адресного блока.
 * Нужен, потому что внутри адреса есть несколько полей с одинаковыми названиями:
 * Street, City, State/Region, Postal Code, Country.
 */
@Log4j2
public class AddressField {

    private final WebDriverWait wait;
    private final String addressBlockLabel;

    /**
     * Создаёт wrapper для конкретного блока адреса, например Billing Address или Primary Address.
     */
    public AddressField(WebDriver driver, String addressBlockLabel) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.addressBlockLabel = addressBlockLabel;
    }

    /**
     * Заполняет одно поле внутри выбранного адресного блока.
     * Например, внутри Billing Address можно заполнить Street или City.
     */
    public void write(String fieldLabel, String text) {
        log.info("Writing '{}' in to '{}' field in '{}'", text, fieldLabel, addressBlockLabel);
        WebElement field = wait.until(ExpectedConditions.elementToBeClickable(fieldByLabels(fieldLabel)));
        field.clear();
        field.sendKeys(text);
    }

    /**
     * Возвращает локатор поля по двум текстам:
     * названию адресного блока и названию конкретного поля внутри него.
     */
    private By fieldByLabels(String fieldLabel) {
        return By.xpath(
                "//form[@id='EditView']" +
                        "//fieldset[.//legend[contains(normalize-space(.), '" + addressBlockLabel + "')]]" +
                        "//tr[.//label[contains(normalize-space(.), '" + fieldLabel + "')]]" +
                        "//*[self::input or self::textarea]" +
                        "[not(@type='hidden') and not(@type='button') and not(@type='submit')][1]"
        );
    }
}
