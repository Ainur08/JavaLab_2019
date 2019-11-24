package ru.multiclientchat.dao;

import com.sun.org.apache.xpath.internal.operations.Or;
import ru.multiclientchat.config.ConnectionConfig;
import ru.multiclientchat.model.Order;
import ru.multiclientchat.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class OrderDaoImpl implements OrderDao {
    private final ConnectionConfig CONFIG = ConnectionConfig.getInstance();
    private final String SAVE = "INSERT INTO public.order (user_id, name, price) VALUES (?, ?, ?)";
    private final String FINDBYUSERID = "SELECT * FROM public.order WHERE user_id = ?";

    @Override
    public void save(Order model) {
        Connection connection = CONFIG.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(SAVE)) {
            stmt.setInt(1, model.getUserId());
            stmt.setString(2, model.getName());
            stmt.setInt(3, model.getPrice());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void delete(Order model) {

    }

    @Override
    public LinkedList<Order> findByUserId(Integer userId) {
        Connection connection = CONFIG.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(FINDBYUSERID)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                LinkedList<Order> orders = new LinkedList<>();
                while (rs.next()) {
                    Order order = new Order();
                    order.setId(rs.getInt("id"));
                    order.setUserId(rs.getInt("user_id"));
                    order.setName(rs.getString("name"));
                    order.setPrice(rs.getInt("price"));
                    orders.add(order);
                }
                return orders;
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
