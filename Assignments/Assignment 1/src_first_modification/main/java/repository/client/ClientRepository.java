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

    boolean save(Client client);

    boolean update(Client client);

    Notification<Client> findByCNP(Long cnp);


    void removeAll();

}
