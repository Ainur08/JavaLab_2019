package ru.multiclientchat.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xpath.internal.operations.Or;
import ru.multiclientchat.dao.OrderDaoImpl;
import ru.multiclientchat.dao.ProductDaoImpl;
import ru.multiclientchat.model.Order;
import ru.multiclientchat.model.Payload;
import ru.multiclientchat.model.Product;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class OrderService {
    public void addProduct(String jsonProduct){
        ObjectMapper objectMapper = new ObjectMapper();
        OrderDaoImpl orderDao = new OrderDaoImpl();
        ProductDaoImpl productDao = new ProductDaoImpl();
        try {
            Payload payload = objectMapper.readValue(jsonProduct, Payload.class);
            LinkedHashMap<String, String> payloadMap = (LinkedHashMap<String, String>)payload.getPayload();
            Product product = productDao.findByName(payloadMap.get("name"));
            orderDao.save(new Order(Integer.parseInt(payloadMap.get("userId")), product.getName(), product.getPrice()));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public String getOrders(Integer userId) {
        LinkedList<Order> orders = new OrderDaoImpl().findByUserId(userId);
        LinkedHashMap<String, Object> payload = new LinkedHashMap<>();
        payload.put("orders", orders);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jacksonOfOrders = objectMapper.writeValueAsString(payload);
            return jacksonOfOrders;
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
    }
}
