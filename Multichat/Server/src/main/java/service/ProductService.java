package service;

import context.Component;
import dao.ProductDao;
import dao.ProductDaoImpl;
import model.Product;
import model.ProductList;
import protocol.Request;
import java.util.LinkedList;

public class ProductService implements Component {
    private ProductDao productDao;

    public void addProduct(Request request) {
        Product product = new Product();
        product.setName(request.getParameter("name"));
        product.setPrice(Integer.valueOf(request.getParameter("price")));
        productDao.save(product);
    }

    public void deleteProduct(Request request) {
        Product product = new Product();
        product.setName(request.getParameter("name"));
        productDao.delete(product);
    }

    public ProductList getProducts() {
        LinkedList<Product> products = productDao.findAll();
        ProductList productList = new ProductList();
        productList.setProducts(products);
        return productList;
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


