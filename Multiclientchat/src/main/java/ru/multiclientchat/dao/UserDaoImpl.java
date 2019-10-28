package ru.multiclientchat.dao;

import ru.multiclientchat.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao {
    private Connection connection;
    private static final String SAVE = "INSERT INTO chat_user(login, password) " +
            "VALUES (?, ?)";
    private static final String FIND = "SELECT * FROM shop_user " + "WHERE username = ?";

    public UserDaoImpl(Connection connection) {
        this.connection = connection;
    }

    public void save(User model) {
        try (PreparedStatement stmt = connection.prepareStatement(SAVE)) {
            String login = model.getLogin();
            String password = model.getPassword();
            stmt.setString(1, login);
            stmt.setString(2, password);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public User findOneByUsername(String username) {
        try (PreparedStatement stmt = connection.prepareStatement(FIND)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                User user = null;
                if (rs.next()) {
                    user.setId(rs.getInt("id"));
                    user.setLogin(rs.getString("login"));
                    user.setPassword(rs.getString("password"));
                    return user;
                }
                return null;
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
