package controller;

import model.Account;
import model.Client;
import model.builder.AccountBuilder;
import model.builder.ClientBuilder;
import model.validation.Notification;
import repository.account.AccountRepositoryMySQL;
import repository.client.ClientRepositoryMySQL;
import service.account.AccountService;
import service.client.ClientService;
import view.EmployeeView;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class EmployeeController {

        private final EmployeeView employeeView;
        private final Connection connection;
        private final ClientService clientService;
        private final AccountService accountService;

        public EmployeeController(EmployeeView employeeView, Connection connection, ClientService clientService, AccountService accountService) {
            this.employeeView = employeeView;
            this.connection = connection;
            this.clientService = clientService;
            this.accountService = accountService;
            employeeView.setBtnClientButtonListener(new EmployeeController.BtnClientButtonListener());
            employeeView.setBtnAccountButtonListener(new EmployeeController.BtnAccountButtonListener());
            employeeView.setBtnTransferButtonListener(new EmployeeController.BtnTransferButtonListener());
            employeeView.setBtnBillsButtonListener(new EmployeeController.BtnBillsButtonListener());

        }


private class BtnClientButtonListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {

        Choice c = employeeView.getClientChoice();
        String data = c.getItem(c.getSelectedIndex());
        switch (data) {
            case "View Client": {

                ClientRepositoryMySQL clientRepository = new ClientRepositoryMySQL(connection);
                String clientName = employeeView.getClientName();
                for (Client client : clientRepository.findAll()) {
                    if (client.getName().equals(clientName)) {
                        JOptionPane.showMessageDialog(employeeView.getContentPane(), "The searched Client has " +
                                "the following information\n\n " + "\tCLIENT FULL NAME:  " +
                                client.getName() + " \n CLIENT IDENTITY CARD NUMBER: " + client.getIdentityCardNb() +
                                "\n CLIENT CNP: " + client.getCNP() + "\n CLIENT COMPLETE ADDRESS: " + client.getAddress());
                        break;
                    }
                }
                break;
            }
            case "Add Client": {
                String clientName = employeeView.getClientName();
                long idCardClient = employeeView.getClientID_Card();
                long cnpClient = employeeView.getClientCNP();
                String clientAddress = employeeView.getClientAddress();
                Client client = new ClientBuilder()
                        .setName(clientName)
                        .setIdentity_cardNr(idCardClient)
                        .setCNP(cnpClient)
                        .setAddress(clientAddress)
                        .build();
                ClientRepositoryMySQL clientRepository = new ClientRepositoryMySQL(connection);
                Notification<Boolean> addClientNotification = clientService.insertClient(clientName, idCardClient, cnpClient, clientAddress);

                if (addClientNotification.hasErrors()) {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), addClientNotification.getFormattedErrors());
                } else {
                    clientRepository.insertClient(client);
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "The Client *** " +
                            client.getName() + " *** was added!");
                    employeeView.clearFieldsClient();
                }
                break;
            }
            case "Update Client": {

                ClientRepositoryMySQL clientRepository = new ClientRepositoryMySQL(connection);
                String clientName = employeeView.getClientName();
                ArrayList<String> clientNames = new ArrayList<>();
                for (Client client : clientRepository.findAll()) {
                    clientNames.add(client.getName());
                }
                if (clientNames.contains(clientName)) {

                    long idCardClient = employeeView.getClientID_Card();
                    long cnpClient = employeeView.getClientCNP();
                    String clientAddress = employeeView.getClientAddress();
                    Client client = new ClientBuilder()
                            .setName(clientName)
                            .setIdentity_cardNr(idCardClient)
                            .setCNP(cnpClient)
                            .setAddress(clientAddress)
                            .build();

                    Notification<Boolean> updateClientNotification = clientService.updateClient(clientName, idCardClient, cnpClient, clientAddress);

                    if (updateClientNotification.hasErrors()) {
                        JOptionPane.showMessageDialog(employeeView.getContentPane(), updateClientNotification.getFormattedErrors());
                    } else {
                        clientRepository.updateClient(client);
                        JOptionPane.showMessageDialog(employeeView.getContentPane(), "The Client *** " +
                                client.getName() + " *** was updated!");
                        employeeView.clearFieldsClient();
                    }
                } else {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "The Client does not exist in our database!");
                }
                break;
            }
            default:
                System.out.println("");
                break;
        }
    }
}

private class BtnAccountButtonListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {

        Choice c2 = employeeView.getAccountChoice();
        String data2 = c2.getItem(c2.getSelectedIndex());

        switch (data2) {
            case "Create Account": {

                long identificationCard = employeeView.getCardIdentification();
                String cardType = employeeView.getCardType();
                int amountMoney = employeeView.getAmountMoney();

                ArrayList<String> possibleTypes = new ArrayList<String>();
                possibleTypes.add("bills");
                possibleTypes.add("savings");
                AccountRepositoryMySQL accountRepository = new AccountRepositoryMySQL(connection);
                ArrayList<Long> listCardID = new ArrayList<>();
                for (Account account : accountRepository.findAll()) {
                    listCardID.add(account.getIdentification_nb());
                }
                    String data = employeeView.getDate();
                String clientName = employeeView.getClientName();
                ClientRepositoryMySQL clientRepository = new ClientRepositoryMySQL(connection);
                for (Client client : clientRepository.findAll()) {
                    if (!listCardID.contains(identificationCard) &&
                            client.getName().equals(clientName) && possibleTypes.contains(cardType)) {
                        Account account = new AccountBuilder()
                                .setAmount_of_money(amountMoney)
                                .setIdentification_nb(identificationCard)
                                .setClient_id(client.getId())
                                .setType(cardType)
                                .setDate_of_creation(data)
                                .build();
                        Notification<Boolean> addAccountNotification = accountService.insertAccount(identificationCard,
                                cardType, amountMoney, client.getId(), data);
                        if (!addAccountNotification.hasErrors()) {
                            accountRepository.insertAccount(account, client.getId());
                            JOptionPane.showMessageDialog(employeeView.getContentPane(), "The Account *** " +
                                    account.getIdentification_nb() + " *** was added!");
                            employeeView.clearFieldsAccount();
                            break;
                        }
                    }
                }

                break;
            }
            case "Update Account": {

                AccountRepositoryMySQL accountRepository = new AccountRepositoryMySQL(connection);
                Long cardIdentification = employeeView.getCardIdentification();
                ArrayList<Long> listCardID = new ArrayList<>();
                for (Account account : accountRepository.findAll()) {
                    listCardID.add(account.getIdentification_nb());

                    ArrayList<String> possibleTypes = new ArrayList<String>();
                    possibleTypes.add("bills");
                    possibleTypes.add("savings");

                    String cardType = employeeView.getCardType();
                    int amountMoney = employeeView.getAmountMoney();
                    String data = employeeView.getDate();

                    if (listCardID.contains(cardIdentification) && possibleTypes.contains(cardType)) {

                        Account acc = new AccountBuilder()
                                .setAmount_of_money(amountMoney)
                                .setIdentification_nb(cardIdentification)
                                .setClient_id(account.getClient_id())
                                .setType(cardType)
                                .setDate_of_creation(data)
                                .build();

                        Notification<Boolean> addAccountNotification = accountService.updateAccount(cardIdentification,
                                cardType, amountMoney, account.getClient_id(), data);

                       if (!addAccountNotification.hasErrors()) {
                            accountRepository.updateAccount(acc);
                            JOptionPane.showMessageDialog(employeeView.getContentPane(), "The Account *** " +
                                    account.getIdentification_nb() + " *** was updated!");
                            employeeView.clearFieldsAccount();
                            break;
                        }
                    } else {
                        JOptionPane.showMessageDialog(employeeView.getContentPane(), "The Account cannot be updated! " +
                                "Check again all fields!");
                        break;
                    }
                }
                break;
            }
            case "Delete Account": {
                AccountRepositoryMySQL accountRepository = new AccountRepositoryMySQL(connection);
                Long cardIdentification = employeeView.getCardIdentification();
                Account account = new AccountBuilder()
                        .setIdentification_nb(cardIdentification)
                        .build();

                accountRepository.deleteAccount(account);
                JOptionPane.showMessageDialog(employeeView.getContentPane(), "The Account was deleted!!");
                employeeView.clearFieldsAccount();
                break;
            }
            case "View Account": {
                AccountRepositoryMySQL accountRepository = new AccountRepositoryMySQL(connection);
                Long cardIdentificationNb = employeeView.getCardIdentification();
                for (Account account : accountRepository.findAll()) {
                    if (account.getIdentification_nb().equals(cardIdentificationNb)) {
                        JOptionPane.showMessageDialog(employeeView.getContentPane(), "The searched Account has " +
                                "the following information\n\n " + "\tBELONG TO THE CLIENT :" + account.getClient_id() +
                                "\nCARD IDENTIFICATION NUMBER:  " +
                                account.getIdentification_nb() + " \n CARD TYPE: " + account.getType() +
                                "\n AMOUNT OF MONEY: " + account.getAmount_of_money() + "\n DATE OF CREATION: " + account.getDate_of_creation());
                        break;
                    }
                }
                break;
            }
            default:
                System.out.println("");
                break;
        }
    }
}

private class BtnTransferButtonListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {

        AccountRepositoryMySQL accountRepository = new AccountRepositoryMySQL(connection);
        Long cardID_Receiver = employeeView.getCardID_Receiver();
        Long cardID_Sender = employeeView.getCardID_Sender();

        int amount = employeeView.getAmount_Transfer();
        List<Account> accounts = accountRepository.findAll();
        for (Account account1 : accounts) {
            for (Account account2 : accounts) {
                if (account1.getIdentification_nb().equals(cardID_Sender)
                        && account2.getIdentification_nb().equals(cardID_Receiver)) {
                    int previousAmount1 = account1.getAmount_of_money();
                    int previousAmount2 = account2.getAmount_of_money();
                    if (previousAmount1 > amount) {
                        account2.setAmount_of_money(previousAmount2 + amount);
                        account1.setAmount_of_money(previousAmount1 - amount);
                        accountRepository.makeTransfer(account1, account2, amount);
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "The money from Account1 to Account2 " +
                            "have been successfully transferred!!");
                    employeeView.clearFieldsTransfer();
                    break;
                } else {JOptionPane.showMessageDialog(employeeView.getContentPane(), "Money could not be transferred!!!");
                }
                }
            }
        }
    }
}

    private class BtnBillsButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            AccountRepositoryMySQL accountRepository = new AccountRepositoryMySQL(connection);
            Long cardIdentification = employeeView.getCardID_Bills();
            int a1 = employeeView.getAmount_Bills();
            List<Account> accounts = accountRepository.findAll();
            for (Account account : accounts) {
                if (account.getIdentification_nb().equals(cardIdentification)) {
                    int previousAmount = account.getAmount_of_money();
                    if (previousAmount > a1) {
                        account.setAmount_of_money(previousAmount - a1);
                        accountRepository.payUtilities(account, a1);
                        JOptionPane.showMessageDialog(employeeView.getContentPane(), "The Utilities for the current Account have been paid");
                        employeeView.clearFieldsBills();
                        break;
                    } else {
                        JOptionPane.showMessageDialog(employeeView.getContentPane(), "The Utilities couldn't be paid!!!!");
                    }
                }
            }
        }
    }
}
