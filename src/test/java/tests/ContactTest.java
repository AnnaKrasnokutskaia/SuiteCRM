package tests;

import dto.Contact;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.ContactPage;
import pages.LoginPage;

/**
 * Тест создания Contact и проверки заполненных полей.
 */
public class ContactTest extends BaseTest {

    /**
     * Создаёт Contact, заполняет поля, сохраняет запись и проверяет данные на detail-странице.
     */
    @Test(description = "Создание сущности Contact и проверка заполненных полей")
    public void checkAddNewContact() {
        String suffix = String.valueOf(System.currentTimeMillis()).substring(7);

        Contact contact = Contact.builder()
                .salutation("Mr.")
                .firstName("QA")
                .lastName("Contact " + suffix)
                .officePhone("+7911000" + suffix)
                .mobile("+7912000" + suffix)
                .jobTitle("QA Engineer " + suffix)
                .department("Quality Assurance")
                .fax("8495111" + suffix)
                .email("qa.contact." + suffix + "@example.com")
                .primaryStreet("Primary street " + suffix)
                .primaryCity("Primary city")
                .primaryState("Primary state")
                .primaryPostalCode("300" + suffix)
                .primaryCountry("Primary country")
                .otherStreet("Other street " + suffix)
                .otherCity("Other city")
                .otherState("Other state")
                .otherPostalCode("400" + suffix)
                .otherCountry("Other country")
                .description("Contact description " + suffix)
                .leadSource("Web Site")
                .build();

        ContactPage contactPage = new LoginPage(driver)
                .open()
                .loginAs("will", "will")
                .openAddContactPage()
                .fillContact(contact)
                .save();

        SoftAssert softAssert = new SoftAssert();
        String actualTitle = contactPage.getTitle();
        softAssert.assertTrue(
                actualTitle.equalsIgnoreCase(contact.getFullName()),
                "Заголовок созданного Contact expected [" + contact.getFullName() + "] but found [" + actualTitle + "]"
        );
        softAssert.assertEquals(contactPage.getFieldValue("First Name"), contact.getFirstName(), "First Name");
        softAssert.assertEquals(contactPage.getFieldValue("Last Name"), contact.getLastName(), "Last Name");
        softAssert.assertEquals(contactPage.getFieldValue("Office Phone"), contact.getOfficePhone(), "Office Phone");
        softAssert.assertEquals(contactPage.getFieldValue("Mobile"), contact.getMobile(), "Mobile");
        softAssert.assertEquals(contactPage.getFieldValue("Job Title"), contact.getJobTitle(), "Job Title");
        softAssert.assertEquals(contactPage.getFieldValue("Department"), contact.getDepartment(), "Department");
        softAssert.assertEquals(contactPage.getFieldValue("Fax"), contact.getFax(), "Fax");
        assertContains(softAssert, contactPage.getFieldValue("Email Address"), contact.getEmail(), "Email Address");
        assertContainsAll(softAssert, contactPage.getFieldValue("Primary Address"), "Primary Address",
                contact.getPrimaryStreet(), contact.getPrimaryCity(), contact.getPrimaryState(),
                contact.getPrimaryPostalCode(), contact.getPrimaryCountry());
        assertContainsAll(softAssert, contactPage.getFieldValue("Other Address"), "Other Address",
                contact.getOtherStreet(), contact.getOtherCity(), contact.getOtherState(),
                contact.getOtherPostalCode(), contact.getOtherCountry());
        softAssert.assertEquals(contactPage.getFieldValue("Description"), contact.getDescription(), "Description");

        contactPage.openMoreInformationTab();
        softAssert.assertEquals(contactPage.getFieldValue("Lead Source"), contact.getLeadSource(), "Lead Source");

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
