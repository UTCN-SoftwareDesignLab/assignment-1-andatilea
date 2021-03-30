package service.client;

import model.Client;
import model.builder.ClientBuilder;
import model.validation.ClientValidator;
import repository.client.ClientRepository;
import model.validation.Notification;

import java.util.List;

public class ClientServiceMySQL implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceMySQL(ClientRepository clientRepository) { this.clientRepository = clientRepository; }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Notification<Boolean> save(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Notification<Boolean> update(Client client) {
        return clientRepository.update(client);
    }

    @Override
    public Notification<Client> findByCNP(Long cnp) {
        return clientRepository.findByCNP(cnp);
    }

    @Override
    public Notification<Client> findById(Long id) {
        return clientRepository.findById(id);
    }

}
