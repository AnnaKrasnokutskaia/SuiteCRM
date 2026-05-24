package step;

import dto.Account;
import pages.AccountPage;
import pages.MainPage;

/**
 * Сценарные шаги для создания Account.
 */
public class AccountStep {

    /**
     * Открывает форму Account, заполняет её и сохраняет запись.
     */
    public AccountPage createAccount(MainPage mainPage, Account account) {
        return mainPage
                .openAddAccountPage()
                .fillAccount(account)
                .save();
    }
}
