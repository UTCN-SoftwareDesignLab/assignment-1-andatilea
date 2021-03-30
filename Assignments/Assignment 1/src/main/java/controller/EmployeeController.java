package controller;
import model.Account;
import model.Client;
import model.validation.Notification;
import service.account.AccountService;
import service.client.ClientService;
import view.EmployeeView;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmployeeController {

    private final EmployeeView employeeView;
    private final ClientService clientService;
    private final AccountService accountService;

    // for client
    private static final String VIEW_CLIENT = "View Client" ;
    private static final String ADD_CLIENT = "Add Client";
    private static final String UPDATE_CLIENT = "Update Client";
    // for account
    private static final String CREATE_ACCOUNT = "Create Account";
    private static final String UPDATE_ACCOUNT = "Update Account";
    private static final String DELETE_ACCOUNT = "Delete Account";
    private static final String VIEW_ACCOUNT = "View Account";


    public EmployeeController(EmployeeView employeeView, ClientService clientService, AccountService accountService) {
        this.employeeView = employeeView;
        this.clientService = clientService;
        this.accountService = accountService;
        employeeView.setBtnClientButtonListener(new BtnClientButtonListener());
        employeeView.setBtnAccountButtonListener(new BtnAccountButtonListener());
        employeeView.setBtnTransferButtonListener(new BtnTransferButtonListener());
        employeeView.setBtnBillsButtonListener(new BtnBillsButtonListener());
    }


    private class BtnClientButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            Choice c = employeeView.getClientChoice();
            String data = c.getItem(c.getSelectedIndex());
            switch (data) {

                case VIEW_CLIENT: {

                    Long clientCNP = employeeView.getClientCNP();
                    // we search for the client using the CNP
                    Notification<Client> clientFound = clientService.findByCNP(clientCNP);

                    if (clientFound.hasErrors()) {
                        JOptionPane.showMessageDialog(employeeView.getContentPane(), clientFound.getFormattedErrors());
                    } else {
                        // if the client is found, we set the proper information in the Table View
                        employeeView.setTableView(clientFound.getResult().toString());
                    }
                    break;
                }

                case ADD_CLIENT: {

                    Client client = new Client();
                    client.setName(employeeView.getClientDTO().getName());
                    client.setIdentity_cardNr(employeeView.getClientDTO().getIdentityCardNb());
                    client.setCNP(employeeView.getClientDTO().getCNP());
                    client.setAddress(employeeView.getClientDTO().getAddress());

                    Notification<Boolean> createClientNotification = clientService.save(client);
                    if(createClientNotification.getResult()){

                        JOptionPane.showMessageDialog(employeeView.getContentPane(), "The Client has been added!");
                    } else {
                        JOptionPane.showMessageDialog(employeeView.getContentPane(), "The Client cannot be added!");
                        employeeView.clearFieldsClient();
                    }
                    break;
                }

                case UPDATE_CLIENT: {

                    Client updatedClient = new Client();
                    updatedClient.setName(employeeView.getClientDTO().getName());
                    updatedClient.setIdentity_cardNr(employeeView.getClientDTO().getIdentityCardNb());
                    updatedClient.setCNP(employeeView.getClientDTO().getCNP());
                    updatedClient.setAddress(employeeView.getClientDTO().getAddress());

                    // check if the client exists in the database using the CNP
                    Notification<Client> findClientNotification = clientService.findByCNP(updatedClient.getCNP());

                    // if the client does not exist, show the errors
                    if (findClientNotification.hasErrors()) {
                        JOptionPane.showMessageDialog(employeeView.getContentPane(), findClientNotification.getFormattedErrors());
                    } else {
                            // if the client exists in the database, we can make the update
                        Notification<Boolean> updateClientNotification = clientService.update(updatedClient);

                        if(updateClientNotification.getResult()){

                            JOptionPane.showMessageDialog(employeeView.getContentPane(), "The Client has been updated!");
                        } else {
                            JOptionPane.showMessageDialog(employeeView.getContentPane(), "The Client cannot be updated!");
                            employeeView.clearFieldsClient();
                        }
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
                case CREATE_ACCOUNT: {

                      Long clientCNP = employeeView.getClientCNP();

                      // firstly, we need to check if the client exists before he can own an account
                      Notification<Client> findClientNotification = clientService.findByCNP(clientCNP);
                      // if the client does not exist, show the errors
                      if (findClientNotification.hasErrors()) {
                        JOptionPane.showMessageDialog(employeeView.getContentPane(), findClientNotification.getFormattedErrors());

                      } else {
                          // if the client exist, we can take his id
                          Long clientID = findClientNotification.getResult().getId();

                          Account accountToCreate = new Account();
                          accountToCreate.setIdentification_nb(employeeView.getAccountDTO().getIdentification_nb());
                          accountToCreate.setAmount_of_money(employeeView.getAccountDTO().getAmount_of_money());
                          accountToCreate.setType(employeeView.getAccountDTO().getType());
                          accountToCreate.setDate_of_creation(employeeView.getAccountDTO().getDate_of_creation());
                          accountToCreate.setClient_id(clientID);

                        Notification<Boolean> createAccountNotification = accountService.save(accountToCreate);

                        if(createAccountNotification.getResult()){

                            JOptionPane.showMessageDialog(employeeView.getContentPane(), "The Account has been created!");
                        } else {
                            JOptionPane.showMessageDialog(employeeView.getContentPane(), "The Account cannot be created!");
                            employeeView.clearFieldsAccount();
                        }
                    }
                    break;
                }

                case UPDATE_ACCOUNT: {

                    Long clientCNP = employeeView.getClientCNP();
                    Notification<Client> findClientNotification = clientService.findByCNP(clientCNP);

                    // first I verified if the client exists (before creating an account)
                    if (findClientNotification.hasErrors()) {
                        JOptionPane.showMessageDialog(employeeView.getContentPane(), "The Client has not been found!");

                    } else {
                        // if the client exist, we can take his id
                        Long clientID = findClientNotification.getResult().getId();

                        Account accountToUpdate = new Account();
                        accountToUpdate.setIdentification_nb(employeeView.getAccountDTO().getIdentification_nb());
                        accountToUpdate.setAmount_of_money(employeeView.getAccountDTO().getAmount_of_money());
                        accountToUpdate.setType(employeeView.getAccountDTO().getType());
                        accountToUpdate.setDate_of_creation(employeeView.getAccountDTO().getDate_of_creation());
                        accountToUpdate.setClient_id(clientID);

                        Notification<Boolean> updateAccountNotification = accountService.update(accountToUpdate);

                        if (updateAccountNotification.getResult()) {
                            JOptionPane.showMessageDialog(employeeView.getContentPane(), "The Account has been updated!");

                        } else {
                            JOptionPane.showMessageDialog(employeeView.getContentPane(), "The Account cannot be updated!");
                            employeeView.clearFieldsAccount();
                        }
                    }
                    break;
                }
                case DELETE_ACCOUNT: {

                    Long identificationCard = employeeView.getAccountIdentification();
                    // we need to check only by the identity Account number
                    Notification<Account> accountFound = accountService.findByIdentificationNb(identificationCard);

                    if (accountFound.hasErrors()) {
                        JOptionPane.showMessageDialog(employeeView.getContentPane(), accountFound.getFormattedErrors());

                    } else {
                        // if the account exists, then we can delete it
                        Notification<Boolean> deleteEmployeeNotification = accountService.delete(Long.toString(identificationCard));

                        if (deleteEmployeeNotification.getResult()) {
                            JOptionPane.showMessageDialog(employeeView.getContentPane(), "The Account was deleted!");

                        } else {
                            JOptionPane.showMessageDialog(employeeView.getContentPane(), "The Account couldn't be deleted!");
                        }
                    }
                    break;
                }
                case VIEW_ACCOUNT: {

                    Long identificationCard = employeeView.getAccountIdentification();
                    // we search the account by the Identification Account Number
                    Notification<Account> accountFound = accountService.findByIdentificationNb(identificationCard);

                    if (accountFound.hasErrors()) {
                        JOptionPane.showMessageDialog(employeeView.getContentPane(), accountFound.getFormattedErrors());

                    } else {
                        // if the client is found, we set the proper information in the Table View
                        employeeView.setTableView(accountFound.getResult().toString());
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

            Long cardID_Receiver = employeeView.getAccountIdentification_Receiver();
            Long cardID_Sender = employeeView.getAccountIdentification_Sender();
            int amount = employeeView.getAmount_Transfer();

            Notification<Account> notificationSenderAccount = accountService.findByIdentificationNb(cardID_Sender);
            Notification<Account> notificationReceiverAccount = accountService.findByIdentificationNb(cardID_Receiver);

            if (!notificationSenderAccount.hasErrors() || notificationReceiverAccount.hasErrors()) {

                Account senderAccount = notificationSenderAccount.getResult();
                Account receiverAccount = notificationReceiverAccount.getResult();

                int previousAmountSender = senderAccount.getAmount_of_money();
                int previousAmountReceiver = receiverAccount.getAmount_of_money();
                // if the sender has enough money to make the transfer
                if (previousAmountSender > amount) {
                    receiverAccount.setAmount_of_money(previousAmountReceiver + amount);
                    senderAccount.setAmount_of_money(previousAmountSender - amount);

                    Notification<Boolean> makeTransfer = accountService.makeTransfer(senderAccount, receiverAccount);
                    if (makeTransfer.getResult()) {
                        JOptionPane.showMessageDialog(employeeView.getContentPane(), "Successfully made the transfer");

                    } else {
                        JOptionPane.showMessageDialog(employeeView.getContentPane(), "The transfer could not be made!!!!");
                        employeeView.clearFieldsTransfer();
                    }
                }
            }else { JOptionPane.showMessageDialog(employeeView.getContentPane(), "Check again the Accounts"); }
        }
    }

    private class BtnBillsButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            Long cardIdentification = employeeView.getAccountIdentification_Utilities();
            int amount = employeeView.getAmount_Bills();
            Notification<Account> notificationAccountToUse = accountService.findByIdentificationNb(cardIdentification);
            // if the searched account exists
            if (!notificationAccountToUse.hasErrors()) {
                Account accountToUse = notificationAccountToUse.getResult();
                int previousAmount = accountToUse.getAmount_of_money();
                // if the client has enough money in his account
                if (previousAmount > amount) {
                    accountToUse.setAmount_of_money(previousAmount - amount);
                    Notification<Boolean> payUtilities = accountService.payUtilities(accountToUse);
                    if (payUtilities.getResult()) {
                        JOptionPane.showMessageDialog(employeeView.getContentPane(), "The Utilities for the current Account have been paid");

                    } else {
                        JOptionPane.showMessageDialog(employeeView.getContentPane(), "The Utilities couldn't be paid!!!!");
                        employeeView.clearFieldsBills();
                    }
                }
            }else{
                JOptionPane.showMessageDialog(employeeView.getContentPane(), "Account does not exists");
            }
        }
    }
}
