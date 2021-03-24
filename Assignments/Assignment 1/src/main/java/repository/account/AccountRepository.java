package repository.account;

import model.Account;
import model.Client;
import repository.EntityNotFoundException;

import java.util.List;


public interface AccountRepository {


    void insertAccount(Account account, Long clientID);

    void updateAccount(Account account);

    void deleteAccount(Account account);

    List<Account> findAll();

    void payUtilities(Account account, int amount);

    void makeTransfer(Account account1, Account account2, int amount);

    void removeAll();

    boolean save(Account account, Long clientID);

    boolean update(Account account);

    boolean utilitiesTest(Account account, int amount);

    boolean transferTest(Account account1, Account account2, int amount);

}
