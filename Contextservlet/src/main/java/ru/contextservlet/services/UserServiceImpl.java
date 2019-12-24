package ru.contextservlet.services;

import ru.contextservlet.dto.User;
import ru.contextservlet.repositories.UserRepository;
import ru.contextservlet.repositories.UserRepositoryImpl;
import ru.mycontext.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class UserServiceImpl implements UserService, Component {
    private UserRepository userRepository;

    @Override
    public Integer login(HttpServletRequest req) {
        return userRepository.findByLogin(req.getParameter("login"));
    }

    @Override
    public String getComponentName() {
        return "userServiceImpl";
    }

    public void setUserRepositoryImpl(UserRepositoryImpl userRepository) {
        this.userRepository = userRepository;
    }
}
