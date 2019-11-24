package ru.multiclientchat.service;

import ru.multiclientchat.dao.UserDaoImpl;
import ru.multiclientchat.model.User;

import java.util.LinkedHashMap;

public class LoginService {
    public User login(LinkedHashMap<String, String> authData) {
        String login = authData.get("login");
        UserDaoImpl userDao = new UserDaoImpl();
        User user = userDao.findOneByUsername(login);
        return user;
    }
}
