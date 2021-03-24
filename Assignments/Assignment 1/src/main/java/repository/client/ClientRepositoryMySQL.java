package repository.client;

import model.Account;
import model.Client;
import model.builder.ClientBuilder;
import repository.EntityNotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientRepositoryMySQL implements ClientRepository {

    private final Connection connection;

    public ClientRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insertClient(Client client) {
        try {
            PreparedStatement stmt = connection
                    .prepareStatement("INSERT INTO client values (null, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, client.getName());
            stmt.setLong(2, client.getIdentityCardNb());
            stmt.setLong(3, client.getCNP());
            stmt.setString(4, client.getAddress());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void updateClient(Client client) {

        try {
            PreparedStatement stmt = connection
                    .prepareStatement("UPDATE client SET identityCardNr=?, cnp=?, address=? WHERE nameC=?", Statement.RETURN_GENERATED_KEYS);
            stmt.setLong(1, client.getIdentityCardNb());
            stmt.setLong(2, client.getCNP());
            stmt.setString(3, client.getAddress());
            stmt.setString(4, client.getName());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from client";
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                clients.add(getClientFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clients;
    }

    @Override
    public boolean save(Client client) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT INTO client values (null, ?, ?, ?, ?)");
            insertStatement.setString(1, client.getName());
            insertStatement.setLong(2, client.getIdentityCardNb());
            insertStatement.setLong(3, client.getCNP());
            insertStatement.setString(4, client.getAddress());
            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Client client) {
        try {
            PreparedStatement stmt2 = connection
                    .prepareStatement("UPDATE client SET identityCardNr=?, cnp=?, address=? WHERE nameC=?", Statement.RETURN_GENERATED_KEYS);
            stmt2.setLong(1, client.getIdentityCardNb());
            stmt2.setLong(2, client.getCNP());
            stmt2.setString(3, client.getAddress());
            stmt2.setString(4, client.getName());
            stmt2.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    @Override
    public void removeAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from client where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Client getClientFromResultSet(ResultSet rs) throws SQLException {
        return new ClientBuilder()
                .setId(rs.getLong("id"))
                .setName(rs.getString("nameC"))
                .setIdentity_cardNr(rs.getInt("identityCardNr"))
                .setCNP(rs.getInt("cnp"))
                .setAddress(rs.getString("address"))
                .build();
    }
}
