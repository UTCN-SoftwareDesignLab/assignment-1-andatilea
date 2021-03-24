package controller;
import model.Role;
import model.User;
import model.builder.UserBuilder;
import model.validation.Notification;
import repository.security.RightsRolesRepository;
import repository.user.UserRepositoryMySQL;
import service.user.AuthenticationService;
import view.AdministratorView;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import static database.Constants.Roles.EMPLOYEE;

public class AdministratorController {

    private final AdministratorView adminView;
    private final AuthenticationService authenticationService;
    private final Connection connection;
    private final RightsRolesRepository rightsRolesRepository;


    public AdministratorController(AdministratorView adminView, AuthenticationService authenticationService, Connection connection, RightsRolesRepository rightsRolesRepository) {
        this.adminView = adminView;
        this.authenticationService = authenticationService;
        this.connection = connection;
        this.rightsRolesRepository = rightsRolesRepository;
        adminView.setCreateEmplButtonListener(new AdministratorController.CreateEmplButtonListener());
        adminView.setReadEmplButtonListener(new AdministratorController.ReadEmplButtonListener());
        adminView.setUpdateEmplEmplButtonListener(new AdministratorController.UpdateEmplButtonListener());
        adminView.setDeleteEmplmplButtonListener(new AdministratorController.DeleteEmplButtonListener());
        //adminView.setGenerateRepsButtonListener(new AdministratorController.GenerateRepsButtonListener());
    }

    private class CreateEmplButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = adminView.getUsername();
            String password = adminView.getPassword();

            Notification<Boolean> createEmployeeNotification = authenticationService.register(username, password);

            if (createEmployeeNotification.hasErrors()) {
                JOptionPane.showMessageDialog(adminView.getContentPane(), createEmployeeNotification.getFormattedErrors());
            } else {
                if (!createEmployeeNotification.getResult()) {
                    JOptionPane.showMessageDialog(adminView.getContentPane(), "Employee couldn't be created!!!");
                } else {
                    JOptionPane.showMessageDialog(adminView.getContentPane(), "Employee creation successful!");
                }
            }
        }
    }


    private class ReadEmplButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            UserRepositoryMySQL userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
            String username = adminView.getUsername();
            for (User user : userRepository.findAll()) {
                if (user.getUsername().equals(username)) {
                    JOptionPane.showMessageDialog(adminView.getContentPane(), "The searched Employee has " +
                            "the following information\n\n " + "\tEMPLOYEE USERNAME :" + user.getUsername() +
                            "\nUSER ROLE: " + EMPLOYEE);
                    break;
                }
            }
        }
    }


    private class UpdateEmplButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            String username = adminView.getUsername();
            String newPassword = adminView.getPassword();

            Notification<Boolean> updateEmployeeNotification = authenticationService.update(username, newPassword);

            if (updateEmployeeNotification.hasErrors()) {
                JOptionPane.showMessageDialog(adminView.getContentPane(), updateEmployeeNotification.getFormattedErrors());
            } else {
                if (!updateEmployeeNotification.getResult()) {
                    JOptionPane.showMessageDialog(adminView.getContentPane(), "Employee couldn't be updated!!!");
                } else {
                    JOptionPane.showMessageDialog(adminView.getContentPane(), "Employee update successful!");
                }
            }
        }
    }


    private class DeleteEmplButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = adminView.getUsername();

            UserRepositoryMySQL userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);

            User employee = new UserBuilder()
                    .setUsername(username)
                    .build();

            userRepository.removeEmployee(employee);
            JOptionPane.showMessageDialog(adminView.getContentPane(), "The Employee was deleted!!");

        }
    }
}
