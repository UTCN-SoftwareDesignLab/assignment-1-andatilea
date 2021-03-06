package repository.user;

import model.User;
import model.builder.UserBuilder;
import model.validation.Notification;
import repository.security.RightsRolesRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Tables.USER;

public class UserRepositoryMySQL implements UserRepository {

    private final Connection connection;
    private final RightsRolesRepository rightsRolesRepository;


    public UserRepositoryMySQL(Connection connection, RightsRolesRepository rightsRolesRepository) {
        this.connection = connection;
        this.rightsRolesRepository = rightsRolesRepository;
    }

    @Override
    public List<User> findAll() {

        List<User> users = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from user";
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                users.add(getUserFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    @Override
    public Notification<User> findByUsernameAndPassword(String username, String password) {
        Notification<User> findByUsernameAndPasswordNotification = new Notification<>();
        try {
            Statement statement = connection.createStatement();
            String fetchUserSql = "Select * from `" + USER + "` where `username`=\'" + username + "\' and `password`=\'" + password + "\'";
            ResultSet userResultSet = statement.executeQuery(fetchUserSql);
            if (userResultSet.next()) {
                User user = new UserBuilder()
                        .setUsername(userResultSet.getString("username"))
                        .setPassword(userResultSet.getString("password"))
                        .setRoles(rightsRolesRepository.findRolesForUser(userResultSet.getLong("id")))
                        .build();
                findByUsernameAndPasswordNotification.setResult(user);
                return findByUsernameAndPasswordNotification;
            } else {
                findByUsernameAndPasswordNotification.addError("Invalid email or password!");
                return findByUsernameAndPasswordNotification;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            findByUsernameAndPasswordNotification.addError("Something is wrong with the Database");
        }
        return findByUsernameAndPasswordNotification;
    }

    @Override
    public Notification<Boolean> save(User user) {
        Notification<Boolean> createUserNotification = new Notification<>();
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("INSERT INTO user values (null, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertUserStatement.setString(1, user.getUsername());
            insertUserStatement.setString(2, user.getPassword());
            insertUserStatement.executeUpdate();

            ResultSet rs = insertUserStatement.getGeneratedKeys();
            rs.next();
            long userId = rs.getLong(1);
            user.setId(userId);

            rightsRolesRepository.addRolesToUser(user, user.getRoles());
            createUserNotification.setResult(true);
            return createUserNotification;
        } catch (SQLException e) {
            e.printStackTrace();
            createUserNotification.addError("Something is wrong with the Database");
            return createUserNotification;
        }
    }

    @Override
    public Notification<Boolean> update(User user) {
        Notification<Boolean> updateUserNotification = new Notification<>();
        try {
            PreparedStatement stmt = connection
                    .prepareStatement("UPDATE user SET password=? WHERE username=?", Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, user.getPassword());
            stmt.setString(2, user.getUsername());
            stmt.executeUpdate();
            updateUserNotification.setResult(true);
            return updateUserNotification;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            updateUserNotification.addError("Something is wrong with the Database");
            return updateUserNotification;
        }
    }

    @Override
    public Notification<Boolean> remove(String username) {
        Notification<Boolean> removeUserNotification = new Notification<>();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM  user  WHERE username=?"
            );
            statement.setString(1, username);
            statement.executeUpdate();
            removeUserNotification.setResult(true);
            return removeUserNotification;
        } catch (SQLException e) {
            e.printStackTrace();
            removeUserNotification.addError("Something is wrong with the Database");
            return removeUserNotification;
        }
    }

    @Override
    public Notification<User> findByUsername(String username) {
        Notification<User> findByUsernameNotification = new Notification<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from `" + USER + "` where `username`=\'" + username + "\'";
            ResultSet userResultSet = statement.executeQuery(sql);
            if (userResultSet.next()) {
                User user = new UserBuilder()
                        .setUsername(userResultSet.getString("username"))
                        .build();
                findByUsernameNotification.setResult(user);
                return findByUsernameNotification;
            } else {
                findByUsernameNotification.addError("Invalid username!");
                return findByUsernameNotification;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            findByUsernameNotification.addError("Something is wrong with the Database");
        }
        return findByUsernameNotification;

    }

    @Override
    public void removeAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from user where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private User getUserFromResultSet(ResultSet rs) throws SQLException {
        return new UserBuilder()
                .setUsername(rs.getString("username"))
                .setPassword(rs.getString("password"))
                .build();
    }
}
