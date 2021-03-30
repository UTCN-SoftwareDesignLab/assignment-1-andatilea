package repository.user;
import database.DBConnectionFactory;
import model.Role;
import model.User;
import model.builder.UserBuilder;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import java.sql.Connection;
import java.util.Collections;
import static database.Constants.Roles.EMPLOYEE;
import static org.junit.Assert.*;
public class UserRepositoryTest {


    private static UserRepository userRepository;
    private static RightsRolesRepository rightsRolesRepository;


    @BeforeClass
    public static void setupClass() {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
    }

    @Before
    public void cleanUp() {
        userRepository.removeAll();
    }


    @Test
    public void findAll() {

        Role role = new Role(1l,EMPLOYEE,null);
        User user = new UserBuilder()
                .setUsername("TEST - find all")
                .setPassword("TEST1!")
                .setRoles(Collections.singletonList(role))
                .build();
        userRepository.save(user);
        assertTrue(userRepository.findAll().size() == 1);
    }

    @Test
    public void findByUsernameAndPassword() {

        Role role = new Role(1l,EMPLOYEE,null);

        User user = new UserBuilder()
                .setUsername("TEST - findBy")
                .setPassword("TEST1!")
                .setRoles(Collections.singletonList(role))
                .build();
        userRepository.save(user);

        assertTrue(userRepository.findByUsernameAndPassword("TEST - findBy","TEST1!").getResult() != null);
    }

    @Test
    public void save() {

        Role role = new Role(1l,EMPLOYEE,null);

        User user = new UserBuilder()
                .setUsername("TEST - save")
                .setPassword("TEST1!")
                .setRoles(Collections.singletonList(role))
                .build();
        assertTrue(userRepository.save(user));
    }

    @Test
    public void update() {

        Role role = new Role(1l,EMPLOYEE,null);
        User user = new UserBuilder()
                .setUsername("TEST")
                .setPassword("TEST1!")
                .setRoles(Collections.singletonList(role))
                .build();
        userRepository.save(user);

        // the user can be updated by changing the password
        User updateUser = new UserBuilder()
                .setUsername("TEST")
                .setPassword("TEST1! - update")
                .setRoles(Collections.singletonList(role))
                .build();
        userRepository.update(updateUser);

        assertTrue(userRepository.findAll().size() == 1);
    }

    @Test
    public void remove() {

        Role role = new Role(1l,EMPLOYEE,null);
        User user = new UserBuilder()
                .setUsername("TEST - delete")
                .setPassword("TEST1!")
                .setRoles(Collections.singletonList(role))
                .build();
        userRepository.save(user);
        String username = user.getUsername();
        userRepository.remove(username);
        assertTrue(userRepository.findAll().size() == 0);
    }

    @Test
    public void findByUsername() {

        Role role = new Role(1l,EMPLOYEE,null);
        User user = new UserBuilder()
                .setUsername("TEST - find by username")
                .setPassword("TEST1!")
                .setRoles(Collections.singletonList(role))
                .build();
        userRepository.save(user);
        String username = user.getUsername();
        assertTrue(userRepository.findByUsername(username) != null);
    }
}