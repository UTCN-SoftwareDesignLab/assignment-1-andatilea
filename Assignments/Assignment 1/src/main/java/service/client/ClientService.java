package service.client;

import model.Client;
import model.User;
import model.validation.Notification;

import java.util.List;

public interface ClientService {

    List<Client> findAll();

    Notification<Boolean> save(Client client);

    Notification<Boolean> update(Client client);

    Notification<Client> findByCNP(Long cnp);

    Notification<Client> findById(Long id);
}
