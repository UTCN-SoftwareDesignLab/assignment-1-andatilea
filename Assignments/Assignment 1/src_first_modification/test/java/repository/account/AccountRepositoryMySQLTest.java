package repository.account;

import database.DBConnectionFactory;
import model.Account;
import model.builder.AccountBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class AccountRepositoryMySQLTest {

    private static AccountRepository accountRepository;

    @Before
    public void setup() {
        accountRepository = new AccountRepositoryMySQL(DBConnectionFactory.getConnectionWrapper(true)
                .getConnection());

    }

    @Test
    public void payUtilities() {

        List<Account> allAccounts = accountRepository.findAll();
        for (Account a : allAccounts) {
            if (a.getClient_id().equals(18L)) {
                Account account = new AccountBuilder()
                        .setAmount_of_money(100)
                        .setIdentification_nb(55555L)
                        .setClient_id(18L)
                        .setType("bills")
                        .setDate_of_creation("2021-03-23 20:28:00")
                        .build();
                assertTrue(accountRepository.utilitiesTest(account, 10));
            }
        }
    }

    @Test
    public void makeTransfer() {

                    Account a1 = new AccountBuilder()
                        .setAmount_of_money(100)
                        .setIdentification_nb(55555L)
                        .setClient_id(18L)
                        .setType("bills")
                        .setDate_of_creation("2021-03-23 20:28:00")
                        .build();

                     Account a2 = new AccountBuilder()
                                .setAmount_of_money(205)
                                .setIdentification_nb(77698L)
                                .setClient_id(19L)
                                .setType("savings")
                                .setDate_of_creation("2021-03-23 20:28:00")
                                .build();
                        assertTrue(accountRepository.transferTest(a1, a2, 10));
                    }
    }
