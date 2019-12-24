package ru.contextservlet.repositories;

import ru.contextservlet.configs.ConnectionConfig;
import ru.contextservlet.dto.Product;
import ru.mycontext.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class ProductRepositoryImpl implements ProductRepository, Component {
    private final ConnectionConfig CONFIG = ConnectionConfig.getInstance();
    private final String FINDBYUSERID = "SELECT * FROM public.product WHERE user_id = ?";

    @Override
    public void save(Product model) {

    }

    @Override
    public void update(Product model) {

    }

    @Override
    public void delete(Long aLong) {

    }

    @Override
    public Optional<Product> find(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<Product> findAll() {
        return null;
    }

    @Override
    public List<Product> findByUserLogin(String login) {
        Connection connection = CONFIG.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(FINDBYUSERID)) {
            stmt.setString(1, login);
            try (ResultSet rs = stmt.executeQuery()) {
                List<Product> products = new LinkedList<>();
                while (rs.next()) {
                    Product product = new Product();
                    product.setUserId(rs.getLong("user_id"));
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
    public String getComponentName() {
        return "productRepositoryImpl";
    }
}
