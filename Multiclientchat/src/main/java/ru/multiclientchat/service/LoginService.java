package ru.multiclientchat.service;

import ru.multiclientchat.config.ConnectionConfig;
import ru.multiclientchat.dao.UserDaoImpl;
import ru.multiclientchat.model.User;

import java.util.Scanner;

public class LoginService {
    private static final ConnectionConfig CONFIG = ConnectionConfig.getInstance();
    private UserDaoImpl userDao = new UserDaoImpl(CONFIG.getConnection());

    public User isUserExists() {
        User user = null;
        Scanner scanner = new Scanner(System.in);
        while (user == null) {
            System.out.println("Login");
            System.out.print("username: ");
            String login = scanner.nextLine();
            System.out.print("password: ");
            String password = scanner.nextLine();
            user = userDao.findOneByUsername(login);
        }
        userDao.save(user);
        return user;
    }
}

