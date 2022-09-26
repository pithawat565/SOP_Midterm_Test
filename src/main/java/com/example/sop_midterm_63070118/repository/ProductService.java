package com.example.sop_midterm_63070118.repository;

import com.example.sop_midterm_63070118.pojo.Product;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @RabbitListener(queues = "AddProductQueue")
    public boolean addProduct(Product product){
        try {
            this.productRepository.save(product);
            return true;
        }catch (Exception err){
            return false;
        }
    }

    @RabbitListener(queues = "UpdateProductQueue")
    public boolean updateProduct(Product product){
        try {
            this.productRepository.save(product);
            return true;
        }catch (Exception err){
            return false;
        }
    }

    @RabbitListener(queues = "DeleteProductQueue")
    public boolean deleteProduct(Product product){
        try{
            this.productRepository.delete(product);
            return true;
        }catch (Exception err){
            return false;
        }
    }

    @RabbitListener(queues = "GetAllProductQueue")
    public List<Product> getAllProduct(){
        try{
            return this.productRepository.findAll();
        }
        catch (Exception err){
            return null;
        }
    }

    @RabbitListener(queues = "GetNameProductQueue")
    public Product getProductByName(String name){
        try{
            return this.productRepository.findByName(name);
        }
        catch (Exception err){
            return null;
        }
    }
}
