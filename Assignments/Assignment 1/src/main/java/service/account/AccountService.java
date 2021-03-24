package service.account;

import model.validation.Notification;

public interface AccountService {

    Notification<Boolean> insertAccount(Long identificationCard, String type, int amount, Long clientID, String date);

    Notification<Boolean> updateAccount(Long identificationCard, String type, int amount, Long clientID, String date);

}
