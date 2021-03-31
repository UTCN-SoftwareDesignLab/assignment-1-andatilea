package repository.client;
import database.DBConnectionFactory;
import model.Client;
import model.builder.ClientBuilder;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.sql.Connection;
import static org.junit.Assert.*;

public class ClientRepositoryMySQLTest {

    private static ClientRepository clientRepository;


    @BeforeClass
    public static void setupClass() {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        clientRepository = new ClientRepositoryMySQL(connection);
    }

    @Before
    public void cleanUp() {
        clientRepository.removeAll();
    }

    @Test
    public void findAll() {
        Client client =  new ClientBuilder()
                .setName("Client Test Find-All")
                .setCNP(123456789L)
                .setIdentity_cardNr(12345L)
                .setAddress("Address Test Find-All")
                .build();
        clientRepository.save(client);
        assertTrue(clientRepository.findAll().size() == 1);
    }

    @Test
    public void save() {

        Client client =  new ClientBuilder()
                .setName("Client Test-Save")
                .setCNP(123456789L)
                .setIdentity_cardNr(12345L)
                .setAddress("Address Test-Save")
                .build();
        assertTrue(clientRepository.save(client).getResult());
    }

    @Test
    public void update(){
        Client client =  new ClientBuilder()
                .setName("Client Test")
                .setCNP(123456789L)
                .setIdentity_cardNr(12345L)
                .setAddress("Address Test")
                .build();
        clientRepository.save(client);

        Client updateClient =  new ClientBuilder()
                .setName("Client Test-Update")
                .setCNP(123456789L)
                .setIdentity_cardNr(12345L)
                .setAddress("Address Test-Update")
                .build();
        clientRepository.update(updateClient);

        assertTrue(clientRepository.findAll().size() == 1);
    }

    @Test
    public void findByCNP() {

        Client client =  new ClientBuilder()
                .setName("Client Test-Find By CNP")
                .setCNP(123456789L)
                .setIdentity_cardNr(12345L)
                .setAddress("Address Test-Find By CNP")
                .build();
        clientRepository.save(client);
        Long cnp = client.getCNP();
        assertTrue(clientRepository.findByCNP(cnp) != null);
    }

    @Test
    public void findById() {
        Client client =  new ClientBuilder()
                .setName("Client Test-Find By ID")
                .setCNP(123456789L)
                .setIdentity_cardNr(12345L)
                .setAddress("Address Test-Find By ID")
                .build();
        clientRepository.save(client);
        Long id = client.getId();
        assertTrue(clientRepository.findById(id) != null);
    }
}