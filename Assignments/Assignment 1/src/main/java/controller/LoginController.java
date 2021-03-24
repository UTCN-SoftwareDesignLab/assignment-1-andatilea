package controller;
import model.Role;
import repository.user.UserRepository;
import service.user.AuthenticationService;
import model.User;
import model.validation.Notification;
import view.AdministratorView;
import view.EmployeeView;
import view.LoginView;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import database.Constants;

public class LoginController {
    private final LoginView loginView;
    private final AuthenticationService authenticationService;
    private UserRepository userRepository;
    public static User user;
    private final AdministratorView administratorView;
    private final EmployeeView employeeView;


    public LoginController(LoginView loginView, AuthenticationService authenticationService, UserRepository userRepository, AdministratorView administratorView, EmployeeView employeeView) {
        this.loginView = loginView;
        this.authenticationService = authenticationService;
        this.userRepository = userRepository;
        this.administratorView = administratorView;
        this.employeeView = employeeView;
        loginView.setLoginButtonListener(new LoginButtonListener());
        loginView.setResetButtonListener(new ResetButtonListener());
        loginView.setShowPassButtonListener(new ShowPassButtonListener());
    }

    private class LoginButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();
            user = userRepository.findByUsernameAndPassword(username, password).getResult();
            Notification<User> loginNotification = authenticationService.login(username, password);
            Notification<User> findUser = userRepository.findByUsernameAndPassword(username, password);
            if (loginNotification.hasErrors() || findUser.hasErrors()) {
                JOptionPane.showMessageDialog(loginView.getContentPane(), loginNotification.getFormattedErrors());
            } else {
                JOptionPane.showMessageDialog(loginView.getContentPane(), "Login successful!");
                List<Role> allRoles = user.getRoles();
                for (Role r : allRoles) {
                    if (r.getRole().equals(Constants.Roles.ADMINISTRATOR)) {
                        administratorView.setVisible();
                        loginView.dispose();
                    } else if (r.getRole().equals(Constants.Roles.EMPLOYEE)) {
                        employeeView.setVisible();
                        loginView.dispose();
                    }
                }
            }
        }
    }


    private class ResetButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == loginView.reset()) {
                loginView.setUsername("");
                loginView.setPassword("");
            }
        }
    }

    private class ShowPassButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if(loginView.pressButton().isSelected()){ loginView.pass().setEchoChar((char) 0); }
            else { loginView.pass().setEchoChar('*');}
        }
    }
}
