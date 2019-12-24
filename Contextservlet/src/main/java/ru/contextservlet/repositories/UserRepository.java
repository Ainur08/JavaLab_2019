package ru.contextservlet.repositories;

import ru.contextservlet.dto.User;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long>{
    Integer findByLogin(String login);
}
