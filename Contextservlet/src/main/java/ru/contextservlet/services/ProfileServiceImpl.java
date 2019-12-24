package ru.contextservlet.services;

import ru.contextservlet.dto.Product;
import ru.contextservlet.repositories.ProductRepository;
import ru.contextservlet.repositories.ProductRepositoryImpl;
import ru.mycontext.Component;

import java.util.List;

public class ProfileServiceImpl implements ProfileService, Component {
    private ProductRepository productRepository;

    public List getProducts(String login) {
        List<Product> orders = productRepository.findByUserLogin(login);
        return orders;
    }

    public ProductRepository getProductRepository() {
        return productRepository;
    }

    public void setProductRepositoryImpl(ProductRepositoryImpl productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public String getComponentName() {
        return "profileServiceImpl";
    }
}
