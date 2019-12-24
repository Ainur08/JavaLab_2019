package ru.contextservlet.repositories;

import ru.contextservlet.dto.Product;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {
    List<Product> findByUserLogin(String login);
}
