package ru.multiclientchat.dao;

import ru.multiclientchat.model.User;

public interface UserDao extends CrudDao<User> {
    User findOneByUsername(String username);
}
