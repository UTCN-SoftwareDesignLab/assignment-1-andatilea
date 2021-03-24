package service.account;

import database.DBConnectionFactory;
import model.Account;
import model.Client;
import model.builder.AccountBuilder;
import model.builder.ClientBuilder;
import org.junit.Before;
import org.junit.Test;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;

import java.util.List;

import static org.junit.Assert.*;

public class AccountServiceMySQLTest {

    private static AccountRepository accountRepository;

    @Before
    public void setup() {
        accountRepository = new AccountRepositoryMySQL(DBConnectionFactory.getConnectionWrapper(true)
                .getConnection());

    }

    @Test
    public void insertAccount() {
        //accountRepository.removeAll();
        Account account = new AccountBuilder()
                        .setIdentification_nb(55555L)
                        .setAmount_of_money(500)
                        .setType("bills")
                        .setDate_of_creation("2021-03-23 20:28:00")
                        .build();
        assertTrue(accountRepository.save(account, 18L));
    }

    @Test
    public void updateAccount() {
        List<Account> allAccounts = accountRepository.findAll();
        for(Account a: allAccounts){
            if(a.getClient_id().equals(18L)){
                Account updated = new AccountBuilder()
                        .setAmount_of_money(12)
                        .setIdentification_nb(55555L)
                        .setClient_id(18L)
                        .setType("bills")
                        .setDate_of_creation("2021-03-23 20:28:00")
                        .build();
                assertTrue(accountRepository.update(updated));
            }
        }
    }
}