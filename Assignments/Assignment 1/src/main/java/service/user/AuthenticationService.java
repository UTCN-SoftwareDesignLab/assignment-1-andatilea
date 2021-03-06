package service.user;

import model.User;
import model.validation.Notification;


public interface AuthenticationService {


    Notification<User> login(String username, String password);

    Notification<Boolean> register(String username, String password);

}
