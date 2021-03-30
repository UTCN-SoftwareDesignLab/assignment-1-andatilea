package service.account;

import model.Account;
import model.validation.Notification;
import repository.account.AccountRepository;

import java.util.List;


public class AccountServiceMySQL implements AccountService{


    private final AccountRepository accountRepository;

    public AccountServiceMySQL(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Notification<Boolean> save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Notification<Boolean> update(Account account) {
        return accountRepository.update(account);
    }

    @Override
    public Notification<Boolean> delete(String identificationNb) {
        return accountRepository.delete(identificationNb);
    }

    @Override
    public  Notification<Boolean> payUtilities(Account account) {
        return accountRepository.payUtilities(account);
    }

    @Override
    public  Notification<Boolean> makeTransfer(Account account1, Account account2) { return accountRepository.makeTransfer(account1,account2); }

    @Override
    public Notification<Account> findByIdentificationNb(Long identificationNb) { return accountRepository.findByIdentificationNb(identificationNb); }
}