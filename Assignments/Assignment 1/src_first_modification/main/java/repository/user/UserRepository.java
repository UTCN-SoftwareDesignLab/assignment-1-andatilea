package repository.user;

import model.User;
import model.validation.Notification;

import java.util.List;

public interface UserRepository {

    List<User> findAll();

    Notification<User> findByUsernameAndPassword(String username, String password);

    boolean save(User user);

    boolean update(User user);

    boolean remove(String username);

    Notification<User> findByUsername(String username);



}
