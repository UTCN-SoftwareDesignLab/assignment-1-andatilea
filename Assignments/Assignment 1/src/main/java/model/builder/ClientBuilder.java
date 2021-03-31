package model.builder;

import model.Client;
import model.dto.ClientDTO;

public class ClientBuilder {

    private Client client;

    public ClientBuilder() {
        client = new Client();
    }

    public ClientBuilder setId(Long id) {
        client.setId(id);
        return this;
    }

    public ClientBuilder setName(String name) {
        client.setName(name);
        return this;
    }

    public ClientBuilder setIdentity_cardNr(long identity_cardNr) {
        client.setIdentity_cardNr(identity_cardNr);
        return this;
    }

    public ClientBuilder setCNP(long cnp) {
        client.setCNP(cnp);
        return this;
    }

    public ClientBuilder setAddress(String address) {
        client.setAddress(address);
        return this;
    }

    public ClientBuilder dtoToClient(ClientDTO clientDTO){

        client.setName(clientDTO.getName());
        client.setIdentity_cardNr(clientDTO.getIdentityCardNb());
        client.setCNP(clientDTO.getCNP());
        client.setAddress(clientDTO.getAddress());
        return this;

    }

    public Client build() {
        return client;
    }
}
