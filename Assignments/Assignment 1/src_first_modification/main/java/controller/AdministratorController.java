package controller;
import model.Role;
import model.User;
import model.builder.UserBuilder;
import model.validation.Notification;
import repository.security.RightsRolesRepository;
import repository.user.UserRepositoryMySQL;
import service.user.AuthenticationService;
import service.user.UserService;
import view.AdministratorView;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import static database.Constants.Roles.EMPLOYEE;

public class AdministratorController {

    private final AdministratorView adminView;
    private final UserService userService;

    public AdministratorController(AdministratorView adminView, UserService userService) {
        this.adminView = adminView;
        this.userService = userService;
        adminView.setCreateEmplButtonListener(new AdministratorController.CreateEmplButtonListener());
        adminView.setReadEmplButtonListener(new AdministratorController.ReadEmplButtonListener());
        adminView.setUpdateEmplEmplButtonListener(new AdministratorController.UpdateEmplButtonListener());
        adminView.setDeleteEmplmplButtonListener(new AdministratorController.DeleteEmplButtonListener());
    }

    private class CreateEmplButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = adminView.getUsername();
            String password = adminView.getPassword();

            User user = new User();
            user.setUsername(username);
            user.setPassword(password);

            boolean createEmployee = userService.save(user);

            if (createEmployee) {
                JOptionPane.showMessageDialog(adminView.getContentPane(), "Employee creation successful!");
            } else {
                    JOptionPane.showMessageDialog(adminView.getContentPane(), "Employee creation NOT successful!");
            }
        }
    }


    private class ReadEmplButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            String username = adminView.getUsername();

            Notification<User> foundUser = userService.findByUsername(username);
            if (foundUser.hasErrors()) {
                JOptionPane.showMessageDialog(adminView.getContentPane(), foundUser.getFormattedErrors());
            } else {
                JOptionPane.showMessageDialog(adminView.getContentPane(), "The user " + username + " has been found!");
            }
        }
    }


    private class UpdateEmplButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            String username = adminView.getUsername();
            String newPassword = adminView.getPassword();

            User user = new User();
            user.setUsername(username);
            user.setPassword(newPassword);

            boolean updateEmployee = userService.update(user);

            if (updateEmployee) {
                JOptionPane.showMessageDialog(adminView.getContentPane(),"Employee update successful!" );
            } else {
                    JOptionPane.showMessageDialog(adminView.getContentPane(), "Employee couldn't be updated!!");
            }
        }
    }


    private class DeleteEmplButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = adminView.getUsername();

            boolean deleteEmployee = userService.remove(username);

            if (deleteEmployee) {
                JOptionPane.showMessageDialog(adminView.getContentPane(), "The Employee was deleted!");
            } else {
                JOptionPane.showMessageDialog(adminView.getContentPane(), "Employee couldn't be deleted!");
            }
        }
    }
}
