package tests;

import dto.Account;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.AccountPage;
import pages.LoginPage;

/**
 * Тест создания Account и проверки заполненных полей.
 */
public class AccountTest extends BaseTest {

    /**
     * Создаёт Account, заполняет поля, сохраняет запись и проверяет данные на detail-странице.
     */
    @Test(description = "Создание сущности Account и проверка заполненных полей")
    public void checkAddNewAccount() {
        //мне лень подключать фейкер
        String suffix = String.valueOf(System.currentTimeMillis()).substring(7);

        Account account = Account.builder()
                .name("QA Account " + suffix)
                .officePhone("+7909000" + suffix)
                .website("https://qa-account-" + suffix + ".example.com")
                .fax("+7495000" + suffix)
                .email("qa.account." + suffix + "@example.com")
                .billingStreet("Billing street " + suffix)
                .billingCity("Billing city")
                .billingState("Billing state")
                .billingPostalCode("100" + suffix)
                .billingCountry("Billing country")
                .shippingStreet("Shipping street " + suffix)
                .shippingCity("Shipping city")
                .shippingState("Shipping state")
                .shippingPostalCode("200" + suffix)
                .shippingCountry("Shipping country")
                .description("Account description " + suffix)
                .type("Customer")
                .industry("Technology")
                .annualRevenue("100000")
                .employees("15")
                .build();

        AccountPage accountPage = new LoginPage(driver)
                .open()
                .loginAs("will", "will")
                .openAddAccountPage()
                .fillAccount(account)
                .save();

        SoftAssert softAssert = new SoftAssert();
        String actualTitle = accountPage.getTitle();
        softAssert.assertTrue(
                actualTitle.equalsIgnoreCase(account.getName()),
                "Заголовок созданного Account expected [" + account.getName() + "] but found [" + actualTitle + "]"
        );
        softAssert.assertEquals(accountPage.getFieldValue("Name"), account.getName(), "Name");
        softAssert.assertEquals(accountPage.getFieldValue("Office Phone"), account.getOfficePhone(), "Office Phone");
        assertContains(softAssert, accountPage.getFieldValue("Website"), account.getWebsite(), "Website");
        softAssert.assertEquals(accountPage.getFieldValue("Fax"), account.getFax(), "Fax");
        assertContains(softAssert, accountPage.getFieldValue("Email Address"), account.getEmail(), "Email Address");
        assertContainsAll(softAssert, accountPage.getFieldValue("Billing Address"), "Billing Address",
                account.getBillingStreet(), account.getBillingCity(), account.getBillingState(),
                account.getBillingPostalCode(), account.getBillingCountry());
        assertContainsAll(softAssert, accountPage.getFieldValue("Shipping Address"), "Shipping Address",
                account.getShippingStreet(), account.getShippingCity(), account.getShippingState(),
                account.getShippingPostalCode(), account.getShippingCountry());
        softAssert.assertEquals(accountPage.getFieldValue("Description"), account.getDescription(), "Description");

        accountPage.openMoreInformationTab();
        softAssert.assertEquals(accountPage.getFieldValue("Type"), account.getType(), "Type");
        softAssert.assertEquals(accountPage.getFieldValue("Industry"), account.getIndustry(), "Industry");
        assertContains(softAssert, accountPage.getFieldValue("Annual Revenue"), account.getAnnualRevenue(), "Annual Revenue");
        softAssert.assertEquals(accountPage.getFieldValue("Employees"), account.getEmployees(), "Employees");

        softAssert.assertAll();
    }

    /**
     * Проверяет, что поле содержит все ожидаемые части текста.
     * Используется для адресов, потому адрес отображается одной большой строкой.
     */
    private void assertContainsAll(SoftAssert softAssert, String actualText, String fieldName, String... expectedParts) {
        for (String expectedPart : expectedParts) {
            assertContains(softAssert, actualText, expectedPart, fieldName);
        }
    }

    /**
     * Проверяет, что фактический текст содержит ожидаемую часть.
     */
    private void assertContains(SoftAssert softAssert, String actualText, String expectedPart, String fieldName) {
        softAssert.assertTrue(
                actualText.contains(expectedPart),
                fieldName + " должен содержать " + expectedPart + ", фактически: " + actualText
        );
    }
}
