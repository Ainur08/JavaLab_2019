package dao;

import model.Product;
import java.util.LinkedList;

public interface ProductDao extends CrudDao<Product> {
    LinkedList<Product> findAll();
    Product findByName(String name);
}
