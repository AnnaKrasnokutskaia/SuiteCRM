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

        Account account = new Account();
        account.setName("QA Account " + suffix);
        account.setOfficePhone("+7909000" + suffix);
        account.setWebsite("https://qa-account-" + suffix + ".example.com");
        account.setFax("+7495000" + suffix);
        account.setEmail("qa.account." + suffix + "@example.com");
        account.setBillingStreet("Billing street " + suffix);
        account.setBillingCity("Billing city");
        account.setBillingState("Billing state");
        account.setBillingPostalCode("100" + suffix);
        account.setBillingCountry("Billing country");
        account.setShippingStreet("Shipping street " + suffix);
        account.setShippingCity("Shipping city");
        account.setShippingState("Shipping state");
        account.setShippingPostalCode("200" + suffix);
        account.setShippingCountry("Shipping country");
        account.setDescription("Account description " + suffix);
        account.setType("Customer");
        account.setIndustry("Technology");
        account.setAnnualRevenue("100000");
        account.setEmployees("15");

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
