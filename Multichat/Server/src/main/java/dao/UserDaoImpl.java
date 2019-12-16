package dao;

import config.ConnectionConfig;
import context.Component;
import model.AuthData;
import model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao, Component {
    private final ConnectionConfig CONFIG = ConnectionConfig.getInstance();
    private final String UPDATE_VERIFIER = "UPDATE chat_user SET verifier = ? WHERE id = ?";
    private final String FIND_VERIFIER_BY_ID = "SELECT verifier FROM chat_user WHERE id = ?";
    private static final String FIND = "SELECT * FROM chat_user WHERE login = ?";
    private final String FIND_BY_ID = "SELECT * FROM chat_user WHERE id = ?";

    @Override
    public void save(User model) {
    }

    @Override
    public void delete(User model) {
    }

    @Override
    public User findOneByUsername(String username) {
        User user = new User();
        Connection connection = CONFIG.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(FIND)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    AuthData authData = new AuthData();
                    authData.setLogin(rs.getString("login"));
                    authData.setPassword(rs.getString("password"));
                    user.setAuthData(authData);
                    user.setId(rs.getInt("id"));
                    user.setName(rs.getString("name"));
                    user.setRole(rs.getString("role"));
                    return user;
                }
                return null;
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void updateVerifier(String verifier, Integer id) {
        Connection connection = CONFIG.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(UPDATE_VERIFIER)) {
            stmt.setString(1, verifier);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public String findVerifierById(Integer id) {
        String verifier = null;
        Connection connection = CONFIG.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(FIND_VERIFIER_BY_ID)) {
            stmt.setInt(1, id);
            ResultSet set = stmt.executeQuery();
            if (set.next()) {
                verifier = set.getString("verifier");
            }
            return verifier;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public User findUserById(Integer id) {
        User user = null;
        Connection connection = CONFIG.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(FIND_BY_ID)) {
            stmt.setInt(1, id);
            ResultSet set = stmt.executeQuery();
            if (set.next()) {
                user = new User();
                user.setId(set.getInt("id"));
                user.setRole(set.getString("role"));
                user.setName(set.getString("name"));
            }
            return user;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public String getComponentName() {
        return null;
    }
}
