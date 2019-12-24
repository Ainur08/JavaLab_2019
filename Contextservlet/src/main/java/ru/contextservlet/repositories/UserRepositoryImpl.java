package ru.contextservlet.repositories;

import ru.contextservlet.configs.ConnectionConfig;
import ru.contextservlet.dto.User;
import ru.mycontext.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository, Component {
    private final ConnectionConfig CONFIG = ConnectionConfig.getInstance();
    private static final String SAVE = "INSERT INTO public.user_shop(login, password) VALUES (?, ?)";
    private static final String FINDBYLOGIN = "SELECT id FROM public.user_shop WHERE login = ?";

    @Override
    public void save(User model) {
        Connection connection = CONFIG.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(SAVE)) {
            stmt.setString(1, model.getLogin());
            stmt.setString(2, model.getPassword());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void update(User model) {

    }

    @Override
    public void delete(Long aLong) {

    }

    @Override
    public Optional<User> find(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public String getComponentName() {
        return "userRepositoryImpl";
    }

    @Override
    public Integer findByLogin(String login) {
        Connection connection = CONFIG.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(FINDBYLOGIN)) {
            stmt.setString(1, login);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
                return null;
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
