package utils;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.concurrent.TimeUnit;

/**
 * Listener TestNG.
 * Пишет в консоль статус тестов и прикладывает скриншот в Allure при падении или пропуске.
 */
@Log4j2
public class TestListener implements ITestListener {

    /**
     * Срабатывает перед стартом каждого теста.
     */
    @Override
    public void onTestStart(ITestResult iTestResult) {
        log.info("======================================== STARTING TEST {} ========================================", iTestResult.getName());
    }

    /**
     * Срабатывает, если тест прошёл успешно.
     */
    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        log.info("======================================== FINISHED TEST {} Duration: {} s ========================================", iTestResult.getName(),
                getExecutionTime(iTestResult));
    }

    /**
     * Срабатывает при падении теста и прикладывает скриншот к Allure.
     */
    @Override
    public void onTestFailure(ITestResult iTestResult) {
        log.error("======================================== FAILED TEST {} Duration: {} s ========================================", iTestResult.getName(),
                getExecutionTime(iTestResult));

        WebDriver driver = (WebDriver) iTestResult.getTestContext().getAttribute("driver");
        if (driver != null) {
            AllureUtils.takeScreenshot(driver);
        }
    }

    /**
     * Срабатывает, если тест был пропущен, и тоже прикладывает скриншот.
     */
    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        log.info("======================================== SKIPPING TEST {} ========================================", iTestResult.getName());
        WebDriver driver = (WebDriver) iTestResult.getTestContext().getAttribute("driver");
        if (driver != null) {
            AllureUtils.takeScreenshot(driver);
        }
    }

    /**
     * Метод интерфейса TestNG. В проекте не используется.
     */
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    /**
     * Срабатывает перед стартом test-блока из testng.xml. В проекте не используется.
     */
    @Override
    public void onStart(ITestContext iTestContext) {

    }

    /**
     * Срабатывает после завершения test-блока из testng.xml. В проекте не используется.
     */
    @Override
    public void onFinish(ITestContext iTestContext) {

    }

    /**
     * Считает время выполнения теста в секундах.
     */
    private long getExecutionTime(ITestResult iTestResult) {
        return TimeUnit.MILLISECONDS.toSeconds(iTestResult.getEndMillis() - iTestResult.getStartMillis());
    }
}
