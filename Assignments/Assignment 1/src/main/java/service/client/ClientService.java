package service.client;

import model.Client;
import model.validation.Notification;

public interface ClientService {

    Notification<Boolean> insertClient(String name, Long identityCard, Long cnp, String address);
    Notification<Boolean> updateClient(String name, Long identityCard, Long cnp, String address);
    boolean save(Client client);
}
