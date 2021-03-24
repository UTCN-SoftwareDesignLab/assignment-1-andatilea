package service.client;

import model.Client;
import model.builder.ClientBuilder;
import model.validation.ClientValidator;
import repository.client.ClientRepository;
import model.validation.Notification;

public class ClientServiceMySQL implements ClientService {

    private final ClientRepository repository;

    public ClientServiceMySQL(ClientRepository repository) {
        this.repository = repository;
    }


    @Override
    public Notification<Boolean> insertClient(String name, Long identityCard, Long cnp, String address) {

        return getBooleanNotification(name, identityCard, cnp, address);
    }

    @Override
    public Notification<Boolean> updateClient(String name, Long identityCard, Long cnp, String address) {
        return getBooleanNotification(name, identityCard, cnp, address);
    }

    @Override
    public boolean save(Client client) {
        return repository.save(client);
    }

    private Notification<Boolean> getBooleanNotification(String name, Long identityCard, Long cnp, String address) {
        Client client = new ClientBuilder()
                .setName(name)
                .setIdentity_cardNr(identityCard)
                .setCNP(cnp)
                .setAddress(address)
                .build();

        ClientValidator clientValidator = new ClientValidator(client);
        boolean clientValid = clientValidator.validate();
        Notification<Boolean> addClientNotification = new Notification<>();

        if (!clientValid) {
            clientValidator.getErrors().forEach(addClientNotification::addError);
            addClientNotification.setResult(Boolean.FALSE);
        }
        return addClientNotification;
    }
}
