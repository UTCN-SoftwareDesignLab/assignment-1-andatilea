package repository.client;

import model.Client;
import repository.EntityNotFoundException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public interface ClientRepository {


    void insertClient(Client client);

    void updateClient(Client client);

    List<Client> findAll();

    boolean save(Client client);

    boolean update(Client client);

    void removeAll();

}
