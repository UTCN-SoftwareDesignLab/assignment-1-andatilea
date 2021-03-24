package repository.account;

import model.Account;
import model.Client;
import model.builder.AccountBuilder;
import repository.EntityNotFoundException;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class AccountRepositoryMySQL implements AccountRepository {

    private final Connection connection;

    public AccountRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insertAccount(Account account, Long clientID) {
        try {
            PreparedStatement stmt = connection
                    .prepareStatement("INSERT INTO account values (null, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, account.getAmount_of_money());
            stmt.setLong(2, account.getIdentification_nb());
            stmt.setLong(3, clientID);
            stmt.setString(4, account.getType());
            stmt.setString ( 5 ,account.getDate_of_creation());
            stmt.executeUpdate();
        }catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void updateAccount(Account account) {
        try {
            PreparedStatement stmt = connection
                    .prepareStatement("UPDATE account SET amount=?, client_id=?, type_ac=?, date_creation=? WHERE identification_nb=?", Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, account.getAmount_of_money());
            stmt.setLong(2, account.getClient_id());
            stmt.setString(3, account.getType());
            stmt.setString ( 4, account.getDate_of_creation());
            stmt.setLong(5, account.getIdentification_nb());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void deleteAccount(Account account) {
        try{
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM  account  WHERE identification_nb=?"
            );
            statement.setLong(1,account.getIdentification_nb());
            statement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();

        }
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
    public void payUtilities(Account account, int amount) {

            try {
                PreparedStatement stmt = connection
                        .prepareStatement("UPDATE account SET amount=? WHERE identification_nb=?", Statement.RETURN_GENERATED_KEYS);
                stmt.setInt(1, account.getAmount_of_money());
                stmt.setLong(2, account.getIdentification_nb());
                stmt.executeUpdate();
            } catch (SQLException e) {
                System.err.println(e.getMessage());

            }
    }

    @Override
    public void makeTransfer(Account account1, Account account2, int amount) {
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

        } catch (SQLException e) {
            System.err.println(e.getMessage());

        }
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

    @Override
    public boolean save(Account account, Long clientID) {
        try {
            PreparedStatement stmt = connection
                    .prepareStatement("INSERT INTO account values (null, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, account.getAmount_of_money());
            stmt.setLong(2, account.getIdentification_nb());
            stmt.setLong(3, clientID);
            stmt.setString(4, account.getType());
            stmt.setString ( 5 ,account.getDate_of_creation());
            stmt.executeUpdate();
            return true;
        }catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Account account) {
        try {
            PreparedStatement stmt2 = connection
                    .prepareStatement("UPDATE account SET amount=?, client_id=?, type_ac=?, date_creation=? WHERE identification_nb=?", Statement.RETURN_GENERATED_KEYS);
            stmt2.setInt(1, account.getAmount_of_money());
            stmt2.setLong(2, account.getClient_id());
            stmt2.setString(3, account.getType());
            stmt2.setString(4, account.getDate_of_creation());
            stmt2.setLong(5, account.getIdentification_nb());
            stmt2.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean utilitiesTest(Account account, int amount) {
        int previousAmount = account.getAmount_of_money();
        if (previousAmount > amount) {
            account.setAmount_of_money(previousAmount - amount);
            try {
                PreparedStatement stmt = connection
                        .prepareStatement("UPDATE account SET amount=? WHERE identification_nb=?", Statement.RETURN_GENERATED_KEYS);
                stmt.setInt(1, account.getAmount_of_money());
                stmt.setLong(2, account.getIdentification_nb());
                stmt.executeUpdate();
                return true;
            } catch (SQLException e) {
                System.err.println(e.getMessage());
                return false;

            }
        }
        return false;
    }

    @Override
    public boolean transferTest(Account account1, Account account2, int amount) {

        int previousAmount1 = account1.getAmount_of_money();
        int previousAmount2 = account2.getAmount_of_money();
        boolean b = false;
        if (previousAmount1 > amount) {
            account2.setAmount_of_money(previousAmount2 + amount);
            account1.setAmount_of_money(previousAmount1 - amount);
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
                b = true;
            } catch (SQLException e) {
                System.err.println(e.getMessage());
                b =  false;
            }
        }
        return b;
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
