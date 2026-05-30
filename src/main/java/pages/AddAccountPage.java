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
import dto.Account;
import wrappers.Textarea;

/**
 * Page Object формы создания Account.
 * Методы страницы заполняют поля формы через wrappers по видимым label.
 */
@Log4j2
public class AddAccountPage extends BasePage {

    /**
     * Передаёт driver в BasePage.
     */
    public AddAccountPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Проверяет, что открылась именно форма создания Account.
     * Сначала ждём форму EditView, потом обязательное поле Name.
     */
    public AddAccountPage waitForPageOpened() {
        waitForVisible(By.cssSelector("#pagecontent[data-module='Accounts'] form#EditView"));
        waitForVisible(By.xpath("//form[@id='EditView']//div[contains(@class, 'edit-view-row-item') and .//div[contains(@class, 'label') and contains(normalize-space(.), 'Name')]]"));
        return this;
    }


    /**
     * Заполняет форму данными из DTO Account.
     * Метод возвращает эту же страницу, чтобы сохранить цепочку вызовов.
     */
    @Step("Заполнить форму Account")
    public AddAccountPage fillAccount(Account account) {
        log.info("Filling account form with account'{}'", account);
        return fillName(account.getName())
                .fillOfficePhone(account.getOfficePhone())
                .fillWebsite(account.getWebsite())
                .fillFax(account.getFax())
                .fillEmail(account.getEmail())
                .fillBillingAddress(
                        account.getBillingStreet(),
                        account.getBillingCity(),
                        account.getBillingState(),
                        account.getBillingPostalCode(),
                        account.getBillingCountry())
                .fillShippingAddress(
                        account.getShippingStreet(),
                        account.getShippingCity(),
                        account.getShippingState(),
                        account.getShippingPostalCode(),
                        account.getShippingCountry())
                .fillDescription(account.getDescription())
                .selectType(account.getType())
                .selectIndustry(account.getIndustry())
                .fillAnnualRevenue(account.getAnnualRevenue())
                .fillEmployees(account.getEmployees());
    }

    /**
     * Заполняет поле Name.
     */
    @Step("Заполнить Name: {name}")
    public AddAccountPage fillName(String name) {
        new Input(driver, "Name").write(name);
        return this;
    }

    /**
     * Заполняет поле Office Phone.
     */
    @Step("Заполнить Office Phone: {phone}")
    public AddAccountPage fillOfficePhone(String phone) {
        new Input(driver, "Office Phone").write(phone);
        return this;
    }

    /**
     * Заполняет поле Website.
     */
    @Step("Заполнить Website: {website}")
    public AddAccountPage fillWebsite(String website) {
        new Input(driver, "Website").write(website);
        return this;
    }

    /**
     * Заполняет поле Fax.
     */
    @Step("Заполнить Fax: {fax}")
    public AddAccountPage fillFax(String fax) {
        new Input(driver, "Fax").write(fax);
        return this;
    }

    /**
     * Заполняет email через отдельный wrapper, потому что Email Address в SuiteCRM сделан виджетом.
     */
    @Step("Заполнить Email Address: {email}")
    public AddAccountPage fillEmail(String email) {
        new EmailInput(driver, "Email Address").write(email);
        return this;
    }

    /**
     * Заполняет все поля блока Billing Address.
     */
    @Step("Заполнить Billing Address")
    public AddAccountPage fillBillingAddress(String street, String city, String state, String postalCode, String country) {
        AddressField billingAddress = new AddressField(driver, "Billing Address");
        billingAddress.write("Street", street);
        billingAddress.write("City", city);
        billingAddress.write("State/Region", state);
        billingAddress.write("Postal Code", postalCode);
        billingAddress.write("Country", country);
        return this;
    }

    /**
     * Заполняет все поля блока Shipping Address.
     */
    @Step("Заполнить Shipping Address")
    public AddAccountPage fillShippingAddress(String street, String city, String state, String postalCode, String country) {
        AddressField shippingAddress = new AddressField(driver, "Shipping Address");
        shippingAddress.write("Street", street);
        shippingAddress.write("City", city);
        shippingAddress.write("State/Region", state);
        shippingAddress.write("Postal Code", postalCode);
        shippingAddress.write("Country", country);
        return this;
    }

    /**
     * Заполняет поле Description.
     */
    @Step("Заполнить Description: {description}")
    public AddAccountPage fillDescription(String description) {
        new Textarea(driver, "Description").write(description);
        return this;
    }

    /**
     * Выбирает значение в выпадающем списке Type.
     */
    @Step("Выбрать Type: {type}")
    public AddAccountPage selectType(String type) {
        new Select(driver, "Type").select(type);
        return this;
    }

    /**
     * Выбирает значение в выпадающем списке Industry.
     */
    @Step("Выбрать Industry: {industry}")
    public AddAccountPage selectIndustry(String industry) {
        new Select(driver, "Industry").select(industry);
        return this;
    }

    /**
     * Заполняет поле Annual Revenue.
     */
    @Step("Заполнить Annual Revenue: {annualRevenue}")
    public AddAccountPage fillAnnualRevenue(String annualRevenue) {
        new Input(driver, "Annual Revenue").write(annualRevenue);
        return this;
    }

    /**
     * Заполняет поле Employees.
     */
    @Step("Заполнить Employees: {employees}")
    public AddAccountPage fillEmployees(String employees) {
        new Input(driver, "Employees").write(employees);
        return this;
    }

    /**
     * Нажимает Save и возвращает detail-страницу созданного Account.
     */
    @Step("Сохранить Account")
    public AccountPage save() {
        new Button(driver, "Save").click();
        return new AccountPage(driver).waitForPageOpened();
    }
}
