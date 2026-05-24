package step;

import dto.Contact;
import pages.ContactPage;
import pages.MainPage;

/**
 * Сценарные шаги для создания Contact.
 */
public class ContactStep {

    /**
     * Открывает форму Contact, заполняет её и сохраняет запись.
     * Цепочка вызовов остаётся внутри Page Object, а шаг возвращает созданную detail-страницу.
     */
    public ContactPage createContact(MainPage mainPage, Contact contact) {
        return mainPage
                .openAddContactPage()
                .fillContact(contact)
                .save();
    }
}
