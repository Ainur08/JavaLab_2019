package ru.multiclientchat.dao;

import ru.multiclientchat.config.ConnectionConfig;
import ru.multiclientchat.model.AuthData;
import ru.multiclientchat.model.Product;
import ru.multiclientchat.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class ProductDaoImpl implements ProductDao {
    private final ConnectionConfig CONFIG = ConnectionConfig.getInstance();
    private final String FINDALL = "SELECT * FROM product";
    private final String SAVE = "INSERT INTO product (name, price) VALUES (?, ?)";
    private final String DELETE = "DELETE FROM product WHERE name = ?";
    private final String FINDBYNAME = "SELECT * FROM product WHERE name = ?";

    @Override
    public LinkedList<Product> findAll() {
        Connection connection = CONFIG.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(FINDALL)) {
            try (ResultSet rs = stmt.executeQuery()) {
                LinkedList<Product> products = new LinkedList<>();
                while (rs.next()) {
                    Product product = new Product();
                    product.setId(rs.getInt("id"));
                    product.setName(rs.getString("name"));
                    product.setPrice(rs.getInt("price"));
                    products.add(product);
                }
                return products;
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Product findByName(String name) {
        Product product = new Product();
        Connection connection = CONFIG.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(FINDBYNAME)) {
            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    product.setId(rs.getInt("id"));
                    product.setName(rs.getString("name"));
                    product.setPrice(rs.getInt("price"));
                    return product;
                }
                return null;
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void save(Product model) {
        Connection connection = CONFIG.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(SAVE)) {
            stmt.setString(1, model.getName());
            stmt.setInt(2, model.getPrice());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void delete(Product model) {
        Connection connection = CONFIG.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(DELETE)) {
            stmt.setString(1, model.getName());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
