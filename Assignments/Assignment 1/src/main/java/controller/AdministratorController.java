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
        adminView.setCreateEmployeeButtonListener(new CreateEmployeeButtonListener());
        adminView.setReadEmployeeButtonListener(new ReadEmployeeButtonListener());
        adminView.setUpdateEmployeeButtonListener(new UpdateEmployeeButtonListener());
        adminView.setDeleteEmployeeButtonListener(new DeleteEmployeeButtonListener());
    }

    private class CreateEmployeeButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            User createUser = new UserBuilder()
                    .dtoToUser(adminView.getUserDTO())
                    .build();

            Notification<Boolean> createEmployee = userService.save(createUser);

            if (createEmployee.getResult()) {
                JOptionPane.showMessageDialog(adminView.getContentPane(), "Employee creation successful!");
            } else {
                    JOptionPane.showMessageDialog(adminView.getContentPane(), "Employee creation NOT successful!");
            }
        }
    }


    private class ReadEmployeeButtonListener implements ActionListener {

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


    private class UpdateEmployeeButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

           User updateUser = new UserBuilder()
                   .dtoToUser(adminView.getUserDTO())
                   .build();

            Notification<Boolean> updateEmployee = userService.update(updateUser);

            if (updateEmployee.getResult()) {
                JOptionPane.showMessageDialog(adminView.getContentPane(),"Employee update successful!" );
            } else {
                    JOptionPane.showMessageDialog(adminView.getContentPane(), "Employee couldn't be updated!!");
            }
        }
    }


    private class DeleteEmployeeButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = adminView.getUsername();

            Notification<Boolean> deleteEmployee = userService.remove(username);

            if (deleteEmployee.getResult()) {
                JOptionPane.showMessageDialog(adminView.getContentPane(), "The Employee was deleted!");
            } else {
                JOptionPane.showMessageDialog(adminView.getContentPane(), "Employee couldn't be deleted!");
            }
        }
    }
}
