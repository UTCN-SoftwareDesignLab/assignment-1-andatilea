package service.client;

import model.Client;
import model.User;
import model.validation.Notification;

import java.util.List;

public interface ClientService {

    List<Client> findAll();

    boolean save(Client client);

    boolean update(Client client);

    Notification<Client> findByCNP(Long cnp);
}
