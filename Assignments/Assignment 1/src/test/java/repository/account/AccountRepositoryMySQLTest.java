package repository.account;

import database.DBConnectionFactory;
import database.JDBConnectionWrapper;
import launcher.ComponentFactory;
import model.Account;
import model.Client;
import model.builder.AccountBuilder;
import model.builder.ClientBuilder;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;

import java.sql.Connection;
import java.util.List;

import static org.junit.Assert.*;

public class AccountRepositoryMySQLTest {

    private static AccountRepository accountRepository;
    private static ClientRepository clientRepository;

    @BeforeClass
    public static void setupClass() {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        accountRepository = new AccountRepositoryMySQL(connection);
        clientRepository = new ClientRepositoryMySQL(connection);
    }

    // firstly, I run the ClientRepository tests
    // aftet all of them are completed, in the database there will exist only the last client

    @Test
    public void findAll() {

        clientRepository.removeAll();

        Client client =  new ClientBuilder()
                .setId(1L)
                .setName("Client TestA")
                .setCNP(123456789L)
                .setIdentity_cardNr(12345L)
                .setAddress("Address Test")
                .build();
        clientRepository.save(client);
        Long clientID = client.getId();
        accountRepository.removeAll();

        Account account =  new AccountBuilder()
                .setIdentification_nb(12345L)
                .setAmount_of_money(100)
                .setClient_id(clientID)
                .setType("bills")
                .setDate_of_creation("2021-03-30 10:10:00")
                .build();
        accountRepository.save(account);
        assertTrue(accountRepository.findAll().size() == 1);
    }

    @Test
    public void save() {

        accountRepository.removeAll();
        Account account =  new AccountBuilder()
                .setIdentification_nb(55555L)
                .setAmount_of_money(500)
                .setClient_id(1L)
                .setType("bills")
                .setDate_of_creation("2021-03-30 10:10:00")
                .build();
        assertTrue(accountRepository.save(account));
    }

    @Test
    public void update(){

        accountRepository.removeAll();
        Account account =  new AccountBuilder()
                .setIdentification_nb(55555L)
                .setAmount_of_money(500)
                .setClient_id(1L)
                .setType("bills")
                .setDate_of_creation("2021-03-30 10:10:00")
                .build();
        accountRepository.save(account);

        Account updateAccount = new AccountBuilder()
                .setIdentification_nb(55555L)
                .setAmount_of_money(10)
                .setClient_id(1L)
                .setType("savings")
                .setDate_of_creation("2021-03-31 10:10:00")
                .build();
        accountRepository.update(updateAccount);

        assertTrue(accountRepository.findAll().size() == 1);
    }

    @Test
    public void delete() {
        accountRepository.removeAll();
        Account account =  new AccountBuilder()
                .setIdentification_nb(55566L)
                .setAmount_of_money(660)
                .setClient_id(20L)
                .setType("bills")
                .setDate_of_creation("2021-03-30 10:10:00")
                .build();
        accountRepository.save(account);
        Long identificationNr = account.getIdentification_nb();
        accountRepository.delete(Long.toString(identificationNr));
        assertTrue(accountRepository.findAll().size() == 0);

    }

    @Test
    public void payUtilities() {

        accountRepository.removeAll();
        Account account =  new AccountBuilder()
                .setIdentification_nb(55566L)
                .setAmount_of_money(660)
                .setClient_id(18L)
                .setType("bills")
                .setDate_of_creation("2021-03-30 10:10:00")
                .build();
        accountRepository.save(account);

        int amountToPay = 10;

        Account accountForBills = new AccountBuilder()
                .setIdentification_nb(55566L)
                .setAmount_of_money(account.getAmount_of_money() - amountToPay)
                .setClient_id(18L)
                .setType("bills")
                .setDate_of_creation("2021-03-30 10:10:00")
                .build();
        accountRepository.payUtilities(accountForBills);
        Long identificationNr = account.getIdentification_nb();
        assertTrue(accountRepository.findByIdentificationNb(identificationNr).getResult().getAmount_of_money() == 650);
    }

    @Test
    public void makeTransfer() {

        accountRepository.removeAll();
        Account accountSender =  new AccountBuilder()
                .setIdentification_nb(55566L)
                .setAmount_of_money(600)
                .setClient_id(18L)
                .setType("bills")
                .setDate_of_creation("2021-03-30 10:10:00")
                .build();
        accountRepository.save(accountSender);

        Account accountReceiver =  new AccountBuilder()
                .setIdentification_nb(55555L)
                .setAmount_of_money(400)
                .setClient_id(19L)
                .setType("savings")
                .setDate_of_creation("2021-03-30 10:10:00")
                .build();
        accountRepository.save(accountReceiver);

        int amountToTransfer = 100;

        Account newAccountReceiver = new AccountBuilder()
                .setIdentification_nb(55555L)
                .setAmount_of_money(accountReceiver.getAmount_of_money() + amountToTransfer)
                .setClient_id(19L)
                .setType("savings")
                .setDate_of_creation("2021-03-30 10:10:00")
                .build();

        Account newAccountSender =  new AccountBuilder()
                .setIdentification_nb(55566L)
                .setAmount_of_money(accountSender.getAmount_of_money() - amountToTransfer)
                .setClient_id(18L)
                .setType("bills")
                .setDate_of_creation("2021-03-30 10:10:00")
                .build();
        accountRepository.makeTransfer(newAccountSender,newAccountReceiver);
        assertTrue(newAccountSender.getAmount_of_money() == newAccountReceiver.getAmount_of_money());
    }

    @Test
    public void findByIdentificationNb() {
        accountRepository.removeAll();

        Account account =  new AccountBuilder()
                .setId(5l)
                .setIdentification_nb(59986L)
                .setAmount_of_money(50)
                .setClient_id(20L)
                .setType("savings")
                .setDate_of_creation("2021-03-30 10:10:00")
                .build();
        accountRepository.save( account);
        Long identificationNr = account.getIdentification_nb();
        assertTrue(accountRepository.findByIdentificationNb(identificationNr) != null);
    }
}