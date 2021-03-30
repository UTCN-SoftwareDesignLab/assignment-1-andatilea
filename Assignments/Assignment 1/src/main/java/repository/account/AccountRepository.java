package repository.account;

import model.Account;
import model.Client;
import model.validation.Notification;
import repository.EntityNotFoundException;

import java.util.List;


public interface AccountRepository {


    List<Account> findAll();

    Notification<Boolean> save(Account account);

    Notification<Boolean> update(Account account);

    Notification<Boolean> delete(String identificationNb);

    Notification<Boolean> payUtilities(Account account);

    Notification<Boolean> makeTransfer(Account account1, Account account2);

    Notification<Account> findByIdentificationNb(Long identificationNb);

    void removeAll();

}
