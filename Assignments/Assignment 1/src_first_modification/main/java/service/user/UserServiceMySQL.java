package service.user;

import model.Role;
import model.User;
import model.validation.Notification;
import repository.security.RightsRolesRepository;
import repository.user.UserRepository;
import static database.Constants.Roles.EMPLOYEE;


import java.util.Collections;
import java.util.List;

public class UserServiceMySQL implements UserService {

    private UserRepository userRepository;
    private RightsRolesRepository rightsRolesRepository;

    public UserServiceMySQL(UserRepository userRepository, RightsRolesRepository rightsRolesRepository) {
        this.userRepository = userRepository;
        this.rightsRolesRepository = rightsRolesRepository;
    }


    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Notification<User> findByUsernameAndPassword(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }

    @Override
    public boolean save(User user) {
        Role employeeRole = rightsRolesRepository.findRoleByTitle(EMPLOYEE);
        user.setRoles(Collections.singletonList(employeeRole));
        return userRepository.save(user);
    }

    @Override
    public boolean update(User user) {
        return userRepository.update(user);
    }

    @Override
    public boolean remove(String username) {
        return userRepository.remove(username);
    }

    @Override
    public Notification<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
