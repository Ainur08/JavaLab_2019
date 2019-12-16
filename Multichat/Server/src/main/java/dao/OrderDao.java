package dao;

import model.Order;
import java.util.LinkedList;

public interface OrderDao extends CrudDao<Order> {
    LinkedList<Order> findByUserId(Integer userId);
}
