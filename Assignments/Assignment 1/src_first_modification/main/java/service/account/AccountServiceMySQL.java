package service.account;

import model.Account;
import model.builder.AccountBuilder;
import model.validation.AccountValidator;
import model.validation.Notification;


public class AccountServiceMySQL implements AccountService{


    @Override
    public Notification<Boolean> insertAccount(Long identificationCard, String type, int amount, Long clientID, String date) {

        return getBooleanNotification(identificationCard, type, amount, clientID, date);
    }

    @Override
    public Notification<Boolean> updateAccount(Long identificationCard, String type, int amount, Long clientID, String date) {
        return getBooleanNotification(identificationCard, type, amount, clientID, date);
    }


    private Notification<Boolean> getBooleanNotification(Long identificationCard, String type, int amount, Long clientID, String date) {
        Account account = new AccountBuilder()
                .setIdentification_nb(identificationCard)
                .setClient_id(clientID)
                .setType(type)
                .setAmount_of_money(amount)
                .setDate_of_creation(date)
                .build();

        AccountValidator accountValidator = new AccountValidator(account);
        boolean accountValid = accountValidator.validate();
        Notification<Boolean> accountNotification = new Notification<>();

        if (!accountValid) {
            accountValidator.getErrors().forEach(accountNotification::addError);
            accountNotification.setResult(Boolean.FALSE);
        }
        return accountNotification;
    }
}