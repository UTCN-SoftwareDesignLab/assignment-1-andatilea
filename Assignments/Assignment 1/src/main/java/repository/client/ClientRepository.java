package repository.client;

import model.Client;
import model.User;
import model.validation.Notification;
import repository.EntityNotFoundException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public interface ClientRepository {


    List<Client> findAll();

    Notification<Boolean> save(Client client);

    Notification<Boolean> update(Client client);

    Notification<Client> findByCNP(Long cnp);

    Notification<Client> findById(Long id);

    void removeAll();
}
