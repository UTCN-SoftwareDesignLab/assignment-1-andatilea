package service.account;

import model.Account;
import model.validation.Notification;

import java.util.List;

public interface AccountService {

    List<Account> findAll();

    Notification<Boolean> save(Account account);

    Notification<Boolean> update(Account account);

    Notification<Boolean> delete(String identificationNb);

    Notification<Boolean> payUtilities(Account account);

    Notification<Boolean> makeTransfer(Account account1, Account account2);

    Notification<Account> findByIdentificationNb(Long identificationNb);
}
