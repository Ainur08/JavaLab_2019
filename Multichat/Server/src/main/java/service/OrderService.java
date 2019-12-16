package service;

import context.Component;
import dao.OrderDao;
import dao.OrderDaoImpl;
import dao.ProductDao;
import dao.ProductDaoImpl;
import model.Order;
import model.OrderList;
import model.Product;
import protocol.Request;
import java.util.LinkedList;

public class OrderService implements Component {
    OrderDao orderDao;
    ProductDao productDao;

    public void addProduct(Request request) {
        Product product = productDao.findByName(request.getParameter("name"));
        orderDao.save(new Order(Integer.parseInt(request.getParameter("userId")), product.getName(), product.getPrice()));
    }

    public OrderList getOrders(Request request) {
        LinkedList<Order> orders = orderDao.findByUserId(Integer.valueOf(request.getParameter("userId")));
        OrderList orderList = new OrderList();
        orderList.setOrders(orders);
        return orderList;
    }

    public OrderDao getOrderDao() {
        return orderDao;
    }

    public void setOrderDao(OrderDaoImpl orderDao) {
        this.orderDao = orderDao;
    }

    public ProductDao getProductDao() {
        return productDao;
    }

    public void setProductDao(ProductDaoImpl productDao) {
        this.productDao = productDao;
    }

    @Override
    public String getComponentName() {
        return null;
    }
}


