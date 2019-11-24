package ru.multiclientchat.dao;

import ru.multiclientchat.model.Product;

import java.util.LinkedList;

public interface ProductDao extends CrudDao<Product> {
    LinkedList<Product> findAll();
    Product findByName(String name);
}
