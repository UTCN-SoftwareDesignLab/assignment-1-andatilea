package controller;

import model.Account;
import model.Client;
import model.User;
import model.builder.AccountBuilder;
import model.builder.ClientBuilder;
import model.validation.ClientValidator;
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
        private final ClientService clientService;
        private final AccountService accountService;

        public EmployeeController(EmployeeView employeeView, ClientService clientService, AccountService accountService) {
            this.employeeView = employeeView;
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

                Long cnp = employeeView.getClientCNP();
                Notification<Client> clientFound = clientService.findByCNP(cnp);
                if (clientFound.hasErrors()) {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), clientFound.getFormattedErrors());
                } else {
                    String stringToPrint = new String();
                    stringToPrint = stringToPrint.concat("\n Name: " + clientFound.getResult().getName() + ", Identification Nr: " +
                            clientFound.getResult().getIdentityCardNb()
                    + ", CNP: " + clientFound.getResult().getCNP() + ", Address: " + clientFound.getResult().getAddress());
                    employeeView.setClientTableView(stringToPrint);
                }
                break;
            }

            case "Add Client": {
                String clientName = employeeView.getClientName();
                long identificationNrClient = employeeView.getIdentificationNbClient();
                long cnpClient = employeeView.getClientCNP();
                String addressClient = employeeView.getClientAddress();

                ClientValidator clientValidator = new ClientValidator();

                // I verified if all the information about the client is correct using the Client Validator
                // If all conditions are satisfied then we can add the client
                if(clientValidator.validateName(clientName) && clientValidator.validateCNP(Long.toString(cnpClient))
                        && clientValidator.validateIdentityCard(Long.toString(identificationNrClient))
                        && clientValidator.validateAddress(addressClient)) {

                    Client client = new ClientBuilder()
                            .setName(clientName)
                            .setIdentity_cardNr(identificationNrClient)
                            .setCNP(cnpClient)
                            .setAddress(addressClient)
                            .build();

                    boolean addClient = clientService.save(client);

                    if (addClient) {
                        JOptionPane.showMessageDialog(employeeView.getContentPane(), "The Client has been added!");
                    } else {
                        JOptionPane.showMessageDialog(employeeView.getContentPane(), "The Client cannot be added!");
                        employeeView.clearFieldsClient();
                    }
                }
                else{
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), clientValidator.getErrors());
                }
                break;
            }
            case "Update Client": {

                long identificationNrClient = employeeView.getIdentificationNbClient();
                long cnpClient = employeeView.getClientCNP();
                String addressClient = employeeView.getClientAddress();
                String clientName = employeeView.getClientName();

                ClientValidator clientValidator = new ClientValidator();

                if(clientValidator.validateCNP(Long.toString(cnpClient))
                        && clientValidator.validateIdentityCard(Long.toString(identificationNrClient))
                        && clientValidator.validateAddress(addressClient)) {

                    Client clientUpdated = new Client();
                    clientUpdated.setIdentity_cardNr(identificationNrClient);
                    clientUpdated.setCNP(cnpClient);
                    clientUpdated.setAddress(addressClient);
                    clientUpdated.setName(clientName);

                    Notification<Client> notification = clientService.findByCNP(clientUpdated.getCNP());
                    if (notification.hasErrors()) {
                        JOptionPane.showMessageDialog(employeeView.getContentPane(), notification.getFormattedErrors());
                    } else {
                        boolean updateClient = clientService.update(clientUpdated);
                        if (!updateClient) {
                            JOptionPane.showMessageDialog(employeeView.getContentPane(), "The Client cannot be updated!");
                            employeeView.clearFieldsClient();
                        } else {
                            JOptionPane.showMessageDialog(employeeView.getContentPane(), "The Client has been updated!");
                            employeeView.clearFieldsClient();
                        }
                    }
                }
                else{
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), clientValidator.getErrors());
                }
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

//                long identificationCard = employeeView.getCardIdentification();
//                String cardType = employeeView.getCardType();
//                int amountMoney = employeeView.getAmountMoney();
//
//                ArrayList<String> possibleTypes = new ArrayList<String>();
//                possibleTypes.add("bills");
//                possibleTypes.add("savings");
//                AccountRepositoryMySQL accountRepository = new AccountRepositoryMySQL(connection);
//                ArrayList<Long> listCardID = new ArrayList<>();
//                for (Account account : accountRepository.findAll()) {
//                    listCardID.add(account.getIdentification_nb());
//                }
//                    String data = employeeView.getDate();
//                String clientName = employeeView.getClientName();
//                ClientRepositoryMySQL clientRepository = new ClientRepositoryMySQL(connection);
//                for (Client client : clientRepository.findAll()) {
//                    if (!listCardID.contains(identificationCard) &&
//                            client.getName().equals(clientName) && possibleTypes.contains(cardType)) {
//                        Account account = new AccountBuilder()
//                                .setAmount_of_money(amountMoney)
//                                .setIdentification_nb(identificationCard)
//                                .setClient_id(client.getId())
//                                .setType(cardType)
//                                .setDate_of_creation(data)
//                                .build();
//                        Notification<Boolean> addAccountNotification = accountService.insertAccount(identificationCard,
//                                cardType, amountMoney, client.getId(), data);
//                        if (!addAccountNotification.hasErrors()) {
//                            accountRepository.insertAccount(account, client.getId());
//                            JOptionPane.showMessageDialog(employeeView.getContentPane(), "The Account *** " +
//                                    account.getIdentification_nb() + " *** was added!");
//                            employeeView.clearFieldsAccount();
//                            break;
//                        }
//                    }
//                }
//
//                break;
            }
            case "Update Account": {

//                AccountRepositoryMySQL accountRepository = new AccountRepositoryMySQL(connection);
//                Long cardIdentification = employeeView.getCardIdentification();
//                ArrayList<Long> listCardID = new ArrayList<>();
//                for (Account account : accountRepository.findAll()) {
//                    listCardID.add(account.getIdentification_nb());
//
//                    ArrayList<String> possibleTypes = new ArrayList<String>();
//                    possibleTypes.add("bills");
//                    possibleTypes.add("savings");
//
//                    String cardType = employeeView.getCardType();
//                    int amountMoney = employeeView.getAmountMoney();
//                    String data = employeeView.getDate();
//
//                    if (listCardID.contains(cardIdentification) && possibleTypes.contains(cardType)) {
//
//                        Account acc = new AccountBuilder()
//                                .setAmount_of_money(amountMoney)
//                                .setIdentification_nb(cardIdentification)
//                                .setClient_id(account.getClient_id())
//                                .setType(cardType)
//                                .setDate_of_creation(data)
//                                .build();
//
//                        Notification<Boolean> addAccountNotification = accountService.updateAccount(cardIdentification,
//                                cardType, amountMoney, account.getClient_id(), data);
//
//                       if (!addAccountNotification.hasErrors()) {
//                            accountRepository.updateAccount(acc);
//                            JOptionPane.showMessageDialog(employeeView.getContentPane(), "The Account *** " +
//                                    account.getIdentification_nb() + " *** was updated!");
//                            employeeView.clearFieldsAccount();
//                            break;
//                        }
//                    } else {
//                        JOptionPane.showMessageDialog(employeeView.getContentPane(), "The Account cannot be updated! " +
//                                "Check again all fields!");
//                        break;
//                    }
//                }
//                break;
            }
            case "Delete Account": {
//                AccountRepositoryMySQL accountRepository = new AccountRepositoryMySQL(connection);
//                Long cardIdentification = employeeView.getCardIdentification();
//                Account account = new AccountBuilder()
//                        .setIdentification_nb(cardIdentification)
//                        .build();
//
//                accountRepository.deleteAccount(account);
//                JOptionPane.showMessageDialog(employeeView.getContentPane(), "The Account was deleted!!");
//                employeeView.clearFieldsAccount();
//                break;
            }
            case "View Account": {
//                AccountRepositoryMySQL accountRepository = new AccountRepositoryMySQL(connection);
//                Long cardIdentificationNb = employeeView.getCardIdentification();
//                for (Account account : accountRepository.findAll()) {
//                    if (account.getIdentification_nb().equals(cardIdentificationNb)) {
//                        JOptionPane.showMessageDialog(employeeView.getContentPane(), "The searched Account has " +
//                                "the following information\n\n " + "\tBELONG TO THE CLIENT :" + account.getClient_id() +
//                                "\nCARD IDENTIFICATION NUMBER:  " +
//                                account.getIdentification_nb() + " \n CARD TYPE: " + account.getType() +
//                                "\n AMOUNT OF MONEY: " + account.getAmount_of_money() + "\n DATE OF CREATION: " + account.getDate_of_creation());
//                        break;
//                    }
//                }
//                break;
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

//        AccountRepositoryMySQL accountRepository = new AccountRepositoryMySQL(connection);
//        Long cardID_Receiver = employeeView.getCardID_Receiver();
//        Long cardID_Sender = employeeView.getCardID_Sender();
//
//        int amount = employeeView.getAmount_Transfer();
//        List<Account> accounts = accountRepository.findAll();
//        for (Account account1 : accounts) {
//            for (Account account2 : accounts) {
//                if (account1.getIdentification_nb().equals(cardID_Sender)
//                        && account2.getIdentification_nb().equals(cardID_Receiver)) {
//                    int previousAmount1 = account1.getAmount_of_money();
//                    int previousAmount2 = account2.getAmount_of_money();
//                    if (previousAmount1 > amount) {
//                        account2.setAmount_of_money(previousAmount2 + amount);
//                        account1.setAmount_of_money(previousAmount1 - amount);
//                        accountRepository.makeTransfer(account1, account2, amount);
//                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "The money from Account1 to Account2 " +
//                            "have been successfully transferred!!");
//                    employeeView.clearFieldsTransfer();
//                    break;
//                } else {JOptionPane.showMessageDialog(employeeView.getContentPane(), "Money could not be transferred!!!");
//                }
//                }
//            }
//        }
    }
}

    private class BtnBillsButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

//            AccountRepositoryMySQL accountRepository = new AccountRepositoryMySQL(connection);
//            Long cardIdentification = employeeView.getCardID_Bills();
//            int a1 = employeeView.getAmount_Bills();
//            List<Account> accounts = accountRepository.findAll();
//            for (Account account : accounts) {
//                if (account.getIdentification_nb().equals(cardIdentification)) {
//                    int previousAmount = account.getAmount_of_money();
//                    if (previousAmount > a1) {
//                        account.setAmount_of_money(previousAmount - a1);
//                        accountRepository.payUtilities(account, a1);
//                        JOptionPane.showMessageDialog(employeeView.getContentPane(), "The Utilities for the current Account have been paid");
//                        employeeView.clearFieldsBills();
//                        break;
//                    } else {
//                        JOptionPane.showMessageDialog(employeeView.getContentPane(), "The Utilities couldn't be paid!!!!");
//                    }
//                }
//            }
        }
    }
}
