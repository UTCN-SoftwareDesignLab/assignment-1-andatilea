package service.client;
import database.DBConnectionFactory;
import model.Client;
import model.builder.ClientBuilder;
import org.junit.Test;
import org.junit.Before;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;
import java.util.List;
import static org.junit.Assert.assertTrue;

public class ClientServiceMySQLTest {

    private static ClientRepository clientRepository;

    @Before
    public void setup() {
        clientRepository = new ClientRepositoryMySQL(DBConnectionFactory.getConnectionWrapper(true)
                .getConnection());

    }

    @Test
    public void insertClient() {
        //clientRepository.removeAll();
        Client client = new ClientBuilder()
                .setName("John Doe")
                .setAddress("Floresti, Cluj Napoca")
                .setIdentity_cardNr(77757L)
                .setCNP(199302378L)
                .build();
        assertTrue(clientRepository.save(client));
    }

    @Test
    public void updateClient() {
        List<Client> allClients = clientRepository.findAll();
        for(Client c: allClients){
            if(c.getName().equals("Corina Tilea")){
                Client updated = new ClientBuilder()
                        .setName("Corina Tilea")
                        .setAddress("Rapsodiei, Cluj Napoca")
                        .setIdentity_cardNr(55556L)
                        .setCNP(123456789L)
                        .build();
                assertTrue(clientRepository.update(updated));
            }
        }
    }
}