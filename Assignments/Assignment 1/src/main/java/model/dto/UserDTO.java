package model.dto;

import model.Role;

import java.util.List;

public class UserDTO {

    private Long id;
    private String username;
    private String password;
    private List<Role> roles;

    public UserDTO(String username, String password) {

        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() { return roles; }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
