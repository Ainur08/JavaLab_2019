package ru.multiclientchat.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.multiclientchat.dao.ProductDaoImpl;
import ru.multiclientchat.model.Payload;
import ru.multiclientchat.model.Product;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class ProductService {
    public void addProduct(String jsonProduct) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Payload payload = objectMapper.readValue(jsonProduct, Payload.class);
            LinkedHashMap<String, String> payloadMap = (LinkedHashMap<String, String>)payload.getPayload();
            Product product = new Product();
            product.setName(payloadMap.get("name"));
            product.setPrice(Integer.parseInt(payloadMap.get("price")));
            ProductDaoImpl productDao = new ProductDaoImpl();
            productDao.save(product);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public void deleteProduct(String jsonProduct){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Payload payload = objectMapper.readValue(jsonProduct, Payload.class);
            LinkedHashMap<String, String> payloadMap = (LinkedHashMap<String, String>)payload.getPayload();
            Product product = new Product();
            product.setName(payloadMap.get("name"));
            ProductDaoImpl productDao = new ProductDaoImpl();
            productDao.delete(product);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public String getProducts() {
        LinkedList<Product> products = new ProductDaoImpl().findAll();
        LinkedHashMap<String, Object> payload = new LinkedHashMap<>();
        payload.put("products", products);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jacksonOfProducts = objectMapper.writeValueAsString(payload);
            return jacksonOfProducts;
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
    }
}
