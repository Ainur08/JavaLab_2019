package ru.multiclientchat.dao;

import ru.multiclientchat.model.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MessageDaoImpl implements MessageDao {
    private Connection connection;
    private static final String SAVE = "INSERT INTO user_message(id, dateTime, message) " +
            "VALUES (?, ?, ?)";

    public MessageDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Message model) {
        try (PreparedStatement stmt = connection.prepareStatement(SAVE)) {
            int idUser = model.getIdUser();
            String dateTime = model.getDateTime();
            String message = model.getMessage();
            stmt.setInt(1, idUser);
            stmt.setString(2, dateTime);
            stmt.setString(3, message);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
