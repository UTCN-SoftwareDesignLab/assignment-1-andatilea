package repository.account;

import model.Account;
import model.Client;
import model.builder.AccountBuilder;
import model.builder.ClientBuilder;
import model.validation.Notification;
import repository.EntityNotFoundException;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Tables.*;

public class AccountRepositoryMySQL implements AccountRepository {

    private final Connection connection;

    public AccountRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }


    @Override
    public List<Account> findAll() {
        List<Account> accounts = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from account";
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                accounts.add(getAccountFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return accounts;
    }

    @Override
    public Notification<Boolean> save(Account account) {
        Notification<Boolean> createAccountNotification = new Notification<>();
        try {
            PreparedStatement stmt = connection
                    .prepareStatement("INSERT INTO account values (null, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, account.getAmount_of_money());
            stmt.setLong(2, account.getIdentification_nb());
            stmt.setLong(3, account.getClient_id());
            stmt.setString(4, account.getType());
            stmt.setString ( 5 ,account.getDate_of_creation());
            stmt.executeUpdate();
            createAccountNotification.setResult(true);
            return createAccountNotification;
        }catch (SQLException e) {
            System.err.println(e.getMessage());
            createAccountNotification.addError("Account cannot be added!");
            return createAccountNotification;
        }
    }

    @Override
    public Notification<Boolean> update(Account account) {
        Notification<Boolean> updateAccountNotification = new Notification<>();
        try {
            PreparedStatement stmt2 = connection
                    .prepareStatement("UPDATE account SET amount=?, client_id=?, type_ac=?, date_creation=? WHERE identification_nb=?", Statement.RETURN_GENERATED_KEYS);
            stmt2.setInt(1, account.getAmount_of_money());
            stmt2.setLong(2, account.getClient_id());
            stmt2.setString(3, account.getType());
            stmt2.setString(4, account.getDate_of_creation());
            stmt2.setLong(5, account.getIdentification_nb());
            stmt2.executeUpdate();
            updateAccountNotification.setResult(true);
            return updateAccountNotification;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            updateAccountNotification.addError("Account cannot be updated!");
            return updateAccountNotification;
        }
    }

    @Override
    public Notification<Boolean> delete(String identificationNb) {
        Notification<Boolean> deleteAccountNotification = new Notification<>();
        try{
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM  account  WHERE identification_nb=?"
            );
            statement.setLong(1,Long.parseLong(identificationNb));
            statement.executeUpdate();
            deleteAccountNotification.setResult(true);
            return deleteAccountNotification;
        } catch (SQLException e){
            e.printStackTrace();
            deleteAccountNotification.addError("Account cannot be deleted!");
            return deleteAccountNotification;
        }
    }

    @Override
    public  Notification<Boolean> payUtilities(Account account) {
        Notification<Boolean> payUtilitiesNotification = new Notification<>();

            try {
                PreparedStatement stmt = connection
                        .prepareStatement("UPDATE account SET amount=? WHERE identification_nb=?", Statement.RETURN_GENERATED_KEYS);
                stmt.setInt(1, account.getAmount_of_money());
                stmt.setLong(2, account.getIdentification_nb());
                stmt.executeUpdate();
                payUtilitiesNotification.setResult(true);
                return payUtilitiesNotification;
            } catch (SQLException e) {
                System.err.println(e.getMessage());
                payUtilitiesNotification.addError("Account cannot pay the utilities!");
                return payUtilitiesNotification;

            }
    }

    @Override
    public  Notification<Boolean> makeTransfer(Account account1, Account account2) {
        Notification<Boolean> transferMoneyNotification = new Notification<>();
        try {
            PreparedStatement stmt1 = connection
                    .prepareStatement("UPDATE account SET amount=? WHERE identification_nb=?", Statement.RETURN_GENERATED_KEYS);

            stmt1.setInt(1, account1.getAmount_of_money());
            stmt1.setLong(2, account1.getIdentification_nb());
            stmt1.executeUpdate();

            PreparedStatement stmt2 = connection
                    .prepareStatement("UPDATE account SET amount=? WHERE identification_nb=?", Statement.RETURN_GENERATED_KEYS);
            stmt2.setInt(1, account2.getAmount_of_money());
            stmt2.setLong(2, account2.getIdentification_nb());
            stmt2.executeUpdate();
            transferMoneyNotification.setResult(true);
            return transferMoneyNotification;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            transferMoneyNotification.addError("The transfer cannot be performed!");
            return transferMoneyNotification;
        }
    }

    @Override
    public Notification<Account> findByIdentificationNb(Long identificationNb) {

        Notification<Account> findByIdentificationNotification = new Notification<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from `" + ACCOUNT + "` where `identification_nb`=\'" + identificationNb + "\'";

            ResultSet rs = statement.executeQuery(sql);

            if (rs.next()) {
               Account account = new AccountBuilder()
                        .setAmount_of_money(rs.getInt("amount"))
                        .setIdentification_nb(rs.getLong("identification_nb"))
                        .setClient_id(rs.getLong("client_id"))
                        .setType(rs.getString("type_ac"))
                        .setDate_of_creation(rs.getString("date_creation"))
                        .build();

                findByIdentificationNotification.setResult(account);
                return findByIdentificationNotification;
            } else {
                findByIdentificationNotification.addError("Account cannot be found!");
                return findByIdentificationNotification;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return findByIdentificationNotification;
    }

    @Override
    public void removeAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from account where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private Account getAccountFromResultSet(ResultSet rs) throws SQLException {
        return new AccountBuilder()
                .setId(rs.getLong("id"))
                .setAmount_of_money(rs.getInt("amount"))
                .setIdentification_nb(rs.getLong("identification_nb"))
                .setClient_id(rs.getLong("client_id"))
                .setType(rs.getString("type_ac"))
                .setDate_of_creation(rs.getString("date_creation"))
                .build();
    }
}
