package repository.user;

import model.User;
import model.validation.Notification;

import java.util.List;

public interface UserRepository {

    List<User> findAll();

    Notification<User> findByUsernameAndPassword(String username, String password);

    Notification<Boolean> save(User user);

    Notification<Boolean> update(User user);

    Notification<Boolean> remove(String username);

    Notification<User> findByUsername(String username);

    void removeAll();

}
