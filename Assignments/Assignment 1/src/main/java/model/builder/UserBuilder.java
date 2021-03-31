package model.builder;

import model.Role;
import model.User;
import model.dto.ClientDTO;
import model.dto.UserDTO;

import java.util.List;

public class UserBuilder {

    private User user;

    public UserBuilder() {
        user = new User();
    }

    public UserBuilder setUsername(String username) {
        user.setUsername(username);
        return this;
    }

    public UserBuilder setPassword(String password) {
        user.setPassword(password);
        return this;
    }

    public UserBuilder setRoles(List<Role> roles) {
        user.setRoles(roles);
        return this;
    }

    public UserBuilder dtoToUser(UserDTO userDTO){

        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        return this;

    }

    public User build() {
        return user;
    }
}
