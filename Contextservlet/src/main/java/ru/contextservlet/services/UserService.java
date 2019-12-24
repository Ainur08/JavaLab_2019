package ru.contextservlet.services;

import ru.contextservlet.dto.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public interface UserService {
    Integer login(HttpServletRequest req);
}
