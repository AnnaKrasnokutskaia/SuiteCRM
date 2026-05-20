package wrappers;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Wrapper для кнопки.
 * Кнопка ищется по видимому тексту, value или title.
 */
public class Button {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final String text;

    /**
     * Создаёт wrapper для кнопки с указанным текстом.
     */
    public Button(WebDriver driver, String text) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.text = text;
    }

    /**
     * Находит кнопку, скроллит её в центр экрана и кликает.
     * Берём нижнюю кнопку Save, потому что верхнюю может перекрыть фиксированное меню.
     */
    public void click() {
        WebElement button = wait.until(ExpectedConditions.presenceOfElementLocated(buttonByText()));
        scrollToCenter(button);
        wait.until(ExpectedConditions.elementToBeClickable(button)).click();
    }

    /**
     * Скроллит элемент в центр экрана, чтобы его не перекрывала верхняя панель.
     */
    private void scrollToCenter(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});",
                element
        );
    }

    /**
     * Возвращает XPath кнопки внутри формы EditView.
     * Используется last(), потому что на форме есть две Save-кнопки: сверху и снизу.
     */
    private By buttonByText() {
        return By.xpath(
                "(//form[@id='EditView']//*[self::input or self::button]" +
                        "[@value='" + text + "' or @title='" + text + "' or normalize-space(.)='" + text + "'])[last()]"
        );
    }

}
