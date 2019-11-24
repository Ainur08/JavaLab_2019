package ru.multiclientchat.dao;

import ru.multiclientchat.model.Order;
import java.util.LinkedList;

public interface OrderDao extends CrudDao<Order> {
    LinkedList<Order> findByUserId(Integer userId);
}
