package dao;

import config.ConnectionConfig;
import context.Component;
import model.Message;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class MessageDaoImpl implements MessageDao, Component {
    private final ConnectionConfig CONFIG = ConnectionConfig.getInstance();
    private static final String SAVE = "INSERT INTO user_message(user_id, dateTime, message) " +
            "VALUES (?, ?, ?)";
    private static final String PAGINATION = "SELECT * FROM user_message ORDER BY datetime LIMIT ? OFFSET ?";

    @Override
    public void save(Message model) {
        Connection connection = CONFIG.getConnection();
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

    @Override
    public void delete(Message model) {

    }

    @Override
    public LinkedList pagination(int limit, int offset) {
        Connection connection = CONFIG.getConnection();
        LinkedList<Message> linkedList = new LinkedList();
        try (PreparedStatement stmt = connection.prepareStatement(PAGINATION)) {
            stmt.setInt(1, limit);
            stmt.setInt(2, limit * offset);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Message message = new Message();
                    message.setIdUser(rs.getInt("user_id"));
                    message.setDateTime(rs.getString("datetime"));
                    message.setMessage(rs.getString("message"));
                    linkedList.add(message);
                }
                return linkedList;
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public String getComponentName() {
        return null;
    }
}
