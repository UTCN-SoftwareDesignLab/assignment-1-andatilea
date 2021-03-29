package service.user;

import database.DBConnectionFactory;
import model.Role;
import model.User;
import model.builder.UserBuilder;
import org.junit.Before;
import org.junit.Test;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;

import java.util.Collections;

import static database.Constants.Roles.EMPLOYEE;
import static org.junit.Assert.*;

public class AuthenticationServiceMySQLTest {

    private static UserRepository userRepository;
    private static RightsRolesRepository rightsRolesRepository;


    @Before
    public void setup() {
        rightsRolesRepository = new RightsRolesRepositoryMySQL(DBConnectionFactory.getConnectionWrapper(true)
                .getConnection());
        userRepository = new UserRepositoryMySQL(DBConnectionFactory.getConnectionWrapper(true)
                .getConnection(), rightsRolesRepository);

    }
    @Test
    public void register() {

        Role employeeRole = rightsRolesRepository.findRoleByTitle(EMPLOYEE);
        //userRepository.removeAll();
        User user = new UserBuilder()
                .setUsername("userTest@gmail.com")
                .setPassword("parolaTest4!")
                .setRoles(Collections.singletonList(employeeRole))
                .build();
        assertTrue(userRepository.save(user));
    }

    @Test
    public void update() {

        Role employeeRole = rightsRolesRepository.findRoleByTitle(EMPLOYEE);

        User user = new UserBuilder()
                .setUsername("userTest@gmail.com")
                .setPassword("parolaTest4!")
                .setRoles(Collections.singletonList(employeeRole))
                .build();
        assertTrue(userRepository.updateEmployee(user));


    }

    @Test
    public void delete() {

        Role employeeRole = rightsRolesRepository.findRoleByTitle(EMPLOYEE);

        User user = new UserBuilder()
                .setUsername("userTest@gmail.com")
                .setPassword("parolaTest4!")
                .setRoles(Collections.singletonList(employeeRole))
                .build();
        assertTrue(userRepository.removeEmployee(user));
    }
}