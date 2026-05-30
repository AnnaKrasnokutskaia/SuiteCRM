package pages;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import wrappers.AddressField;
import wrappers.Button;
import wrappers.EmailInput;
import wrappers.Input;
import wrappers.Select;
import dto.Contact;
import wrappers.Textarea;

/**
 * Page Object формы создания Contact.
 * Методы страницы заполняют поля формы через wrappers по видимым label.
 */
@Log4j2
public class AddContactPage extends BasePage {

    /**
     * Передаёт driver в BasePage.
     */
    public AddContactPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Проверяет, что открылась именно форма создания Contact.
     * Сначала ждём форму EditView, потом обязательное поле Last Name.
     */
    public AddContactPage waitForPageOpened() {
        waitForVisible(By.cssSelector("#pagecontent[data-module='Contacts'] form#EditView"));
        waitForVisible(By.xpath("//form[@id='EditView']//div[contains(@class, 'edit-view-row-item') and .//div[contains(@class, 'label') and contains(normalize-space(.), 'Last Name')]]"));
        return this;
    }


    /**
     * Заполняет форму данными из DTO Contact.
     * Метод возвращает эту же страницу, чтобы сохранить цепочку вызовов.
     */
    @Step("Заполнить форму Contact")
    public AddContactPage fillContact(Contact contact) {
        log.info("Filling contact form with contact '{}'", contact);
        return selectSalutation(contact.getSalutation())
                .fillFirstName(contact.getFirstName())
                .fillLastName(contact.getLastName())
                .fillOfficePhone(contact.getOfficePhone())
                .fillMobile(contact.getMobile())
                .fillJobTitle(contact.getJobTitle())
                .fillDepartment(contact.getDepartment())
                .fillFax(contact.getFax())
                .fillEmail(contact.getEmail())
                .fillPrimaryAddress(
                        contact.getPrimaryStreet(),
                        contact.getPrimaryCity(),
                        contact.getPrimaryState(),
                        contact.getPrimaryPostalCode(),
                        contact.getPrimaryCountry())
                .fillOtherAddress(
                        contact.getOtherStreet(),
                        contact.getOtherCity(),
                        contact.getOtherState(),
                        contact.getOtherPostalCode(),
                        contact.getOtherCountry())
                .fillDescription(contact.getDescription())
                .selectLeadSource(contact.getLeadSource());
    }

    /**
     * Выбирает обращение в select рядом с First Name.
     * На странице нет отдельного label Salutation, поэтому select ищется внутри строки First Name.
     */
    @Step("Выбрать Salutation: {salutation}")
    public AddContactPage selectSalutation(String salutation) {
        new Select(driver, "First Name").select(salutation);
        return this;
    }

    /**
     * Заполняет поле First Name.
     */
    @Step("Заполнить First Name: {firstName}")
    public AddContactPage fillFirstName(String firstName) {
        new Input(driver, "First Name").write(firstName);
        return this;
    }

    /**
     * Заполняет поле Last Name.
     */
    @Step("Заполнить Last Name: {lastName}")
    public AddContactPage fillLastName(String lastName) {
        new Input(driver, "Last Name").write(lastName);
        return this;
    }

    /**
     * Заполняет поле Office Phone.
     */
    @Step("Заполнить Office Phone: {phone}")
    public AddContactPage fillOfficePhone(String phone) {
        new Input(driver, "Office Phone").write(phone);
        return this;
    }

    /**
     * Заполняет поле Mobile.
     */
    @Step("Заполнить Mobile: {mobile}")
    public AddContactPage fillMobile(String mobile) {
        new Input(driver, "Mobile").write(mobile);
        return this;
    }

    /**
     * Заполняет поле Job Title.
     */
    @Step("Заполнить Job Title: {jobTitle}")
    public AddContactPage fillJobTitle(String jobTitle) {
        new Input(driver, "Job Title").write(jobTitle);
        return this;
    }

    /**
     * Заполняет поле Department.
     */
    @Step("Заполнить Department: {department}")
    public AddContactPage fillDepartment(String department) {
        new Input(driver, "Department").write(department);
        return this;
    }

    /**
     * Заполняет поле Fax.
     */
    @Step("Заполнить Fax: {fax}")
    public AddContactPage fillFax(String fax) {
        new Input(driver, "Fax").write(fax);
        return this;
    }

    /**
     * Заполняет email через отдельный wrapper, потому что Email Address в SuiteCRM сделан виджетом.
     */
    @Step("Заполнить Email Address: {email}")
    public AddContactPage fillEmail(String email) {
        new EmailInput(driver, "Email Address").write(email);
        return this;
    }

    /**
     * Заполняет все поля блока Primary Address.
     */
    @Step("Заполнить Primary Address")
    public AddContactPage fillPrimaryAddress(String street, String city, String state, String postalCode, String country) {
        AddressField primaryAddress = new AddressField(driver, "Primary Address");
        primaryAddress.write("Address", street);
        primaryAddress.write("City", city);
        primaryAddress.write("State/Region", state);
        primaryAddress.write("Postal Code", postalCode);
        primaryAddress.write("Country", country);
        return this;
    }

    /**
     * Заполняет все поля блока Other Address.
     */
    @Step("Заполнить Other Address")
    public AddContactPage fillOtherAddress(String street, String city, String state, String postalCode, String country) {
        AddressField otherAddress = new AddressField(driver, "Other Address");
        otherAddress.write("Other Address", street);
        otherAddress.write("City", city);
        otherAddress.write("State/Region", state);
        otherAddress.write("Postal Code", postalCode);
        otherAddress.write("Country", country);
        return this;
    }

    /**
     * Заполняет поле Description.
     */
    @Step("Заполнить Description: {description}")
    public AddContactPage fillDescription(String description) {
        new Textarea(driver, "Description").write(description);
        return this;
    }

    /**
     * Выбирает значение в выпадающем списке Lead Source.
     */
    @Step("Выбрать Lead Source: {leadSource}")
    public AddContactPage selectLeadSource(String leadSource) {
        new Select(driver, "Lead Source").select(leadSource);
        return this;
    }

    /**
     * Нажимает Save и возвращает detail-страницу созданного Contact.
     */
    @Step("Сохранить Contact")
    public ContactPage save() {
        new Button(driver, "Save").click();
        return new ContactPage(driver).waitForPageOpened();
    }
}
