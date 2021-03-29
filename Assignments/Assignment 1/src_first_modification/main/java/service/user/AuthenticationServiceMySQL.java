package service.user;
import model.Role;
import model.User;
import model.builder.UserBuilder;
import model.validation.Notification;
import model.validation.UserValidator;
import repository.security.RightsRolesRepository;
import repository.user.UserRepository;

import java.util.Collections;
import static database.Constants.Roles.EMPLOYEE;

public class AuthenticationServiceMySQL implements AuthenticationService {

    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;

    public AuthenticationServiceMySQL(UserRepository userRepository, RightsRolesRepository rightsRolesRepository) {
        this.userRepository = userRepository;
        this.rightsRolesRepository = rightsRolesRepository;
    }

//    @Override
//    public Notification<Boolean> register(String username, String password) {
//
//        Role employeeRole = rightsRolesRepository.findRoleByTitle(EMPLOYEE);
//        User user = new UserBuilder()
//                .setUsername(username)
//                .setPassword(password)
//                .setRoles(Collections.singletonList(employeeRole))
//                .build();
//
//        UserValidator userValidator = new UserValidator(user);
//        boolean userValid = userValidator.validate();
//        Notification<Boolean> userCreateNotification = new Notification<>();
//
//        if (!userValid) {
//            userValidator.getErrors().forEach(userCreateNotification::addError);
//            userCreateNotification.setResult(Boolean.FALSE);
//        } else {
//            user.setPassword(password);
//            userCreateNotification.setResult(userRepository.save(user));
//        }
//        return userCreateNotification;
//    }

    @Override
    public Notification<User> login(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }

//    public Notification<Boolean> update(String username, String password) {
//
//        Role employeeRole = rightsRolesRepository.findRoleByTitle(EMPLOYEE);
//        User user = new UserBuilder()
//                .setUsername(username)
//                .setPassword(password)
//                .setRoles(Collections.singletonList(employeeRole))
//                .build();
//
//        UserValidator userValidator = new UserValidator(user);
//        boolean userValid = userValidator.validate();
//        Notification<Boolean> userUpdateNotification = new Notification<>();
//
//        if (!userValid) {
//            userValidator.getErrors().forEach(userUpdateNotification::addError);
//            userUpdateNotification.setResult(Boolean.FALSE);
//        } else {
//            user.setPassword(password);
//            userUpdateNotification.setResult(userRepository.updateEmployee(user));;
//        }
//        return userUpdateNotification;
//    }

//    @Override
//    public Notification<Boolean> delete(String username, String password) {
//
//        Role employeeRole = rightsRolesRepository.findRoleByTitle(EMPLOYEE);
//        User user = new UserBuilder()
//                .setRoles(Collections.singletonList(employeeRole))
//                .build();
//
//        Notification<Boolean> userDeleteNotification = new Notification<>();
//
//            userDeleteNotification.setResult(userRepository.removeEmployee(user));
//
//        return userDeleteNotification;
//    }

//    private String encodePassword(String password) {
//        try {
//            MessageDigest digest = MessageDigest.getInstance("SHA-256");
//            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
//            StringBuilder hexString = new StringBuilder();
//
//            for (byte b : hash) {
//                String hex = Integer.toHexString(0xff & b);
//                if (hex.length() == 1) hexString.append('0');
//                hexString.append(hex);
//            }
//
//            return hexString.toString();
//        } catch (Exception ex) {
//            throw new RuntimeException(ex);
//        }
//    }
}
