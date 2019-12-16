package dao;

import model.User;

public interface UserDao extends CrudDao<User> {
    User findOneByUsername(String username);

    void updateVerifier(String verifier, Integer id);

    String findVerifierById(Integer id);

    User findUserById(Integer id);
}

