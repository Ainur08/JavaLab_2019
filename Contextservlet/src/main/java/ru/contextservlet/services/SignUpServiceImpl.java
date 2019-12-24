package ru.contextservlet.services;

import ru.contextservlet.dto.User;
import ru.contextservlet.repositories.UserRepository;
import ru.contextservlet.repositories.UserRepositoryImpl;
import ru.mycontext.Component;

public class SignUpServiceImpl implements SignUpService, Component {
    private UserRepository userRepository;

    public void signUp(User user) {
        userRepository.save(user);
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public void setUserRepositoryImpl(UserRepositoryImpl userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String getComponentName() {
        return "signUpServiceImpl";
    }
}
