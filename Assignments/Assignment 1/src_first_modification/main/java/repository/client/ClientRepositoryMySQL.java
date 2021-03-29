package repository.client;

import model.Account;
import model.Client;
import model.User;
import model.builder.ClientBuilder;
import model.builder.UserBuilder;
import model.validation.Notification;
import repository.EntityNotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Tables.CLIENT;
import static database.Constants.Tables.USER;

public class ClientRepositoryMySQL implements ClientRepository {

    private final Connection connection;

    public ClientRepositoryMySQL(Connection connection) {
        this.connection = connection;
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
                    .prepareStatement("UPDATE client SET nameC=?, identityCardNr=?, cnp=?, address=? WHERE id = ? ", Statement.RETURN_GENERATED_KEYS);
            stmt2.setString(1, client.getName());
            stmt2.setLong(2, client.getIdentityCardNb());
            stmt2.setLong(3, client.getCNP());
            stmt2.setString(4, client.getAddress());
            stmt2.setLong(5, client.getId());
            stmt2.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Notification<Client> findByCNP(Long cnp) {
        Notification<Client> findByCNPNotification = new Notification<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from `" + CLIENT + "` where `cnp`=\'" + cnp + "\'";

            ResultSet rs = statement.executeQuery(sql);

            if (rs.next()) {
                Client client = new ClientBuilder()
                        .setName(rs.getString("nameC"))
                        .setIdentity_cardNr(rs.getLong("identityCardNr"))
                        .setCNP(rs.getLong("cnp"))
                        .setAddress(rs.getString("address"))
                        .build();

                findByCNPNotification.setResult(client);
                return findByCNPNotification;
            } else {
                findByCNPNotification.addError("Client cannot be found!");
                return findByCNPNotification;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return findByCNPNotification;
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
