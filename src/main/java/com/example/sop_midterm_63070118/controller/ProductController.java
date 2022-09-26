package com.example.sop_midterm_63070118.controller;

import com.example.sop_midterm_63070118.pojo.Product;
import com.example.sop_midterm_63070118.repository.CalculatorPriceService;
import org.atmosphere.config.service.Get;
import org.atmosphere.config.service.Post;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class ProductController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping(value = "/addProduct")
    public boolean serviceAddProduct(String productName, double productCost, double productProfit, double productPrice){
        try {
            Product product = new Product(null, productName, productCost, productProfit, productPrice);
            return (boolean) this.rabbitTemplate.convertSendAndReceive("ProductExchange", "add", product);
        }catch (Exception err) {
            return false;
        }
    }

    @PostMapping (value = "/updateProduct")
    public boolean serviceUpdateProduct(String id, String productName, double productCost, double productProfit, double productPrice){
        try{
            Product product = new Product(id, productName, productCost, productProfit, productPrice);
            return (boolean) this.rabbitTemplate.convertSendAndReceive("ProductExchange", "update", product);
        }catch (Exception err){
            return false;
        }
    }

    @PostMapping (value = "/deleteProduct")
    public boolean serviceDeleteProduct(String id, String productName, double productCost, double productProfit, double productPrice){
        try{
            Product product = new Product(id, productName, productCost, productProfit, productPrice);
            return (boolean) this.rabbitTemplate.convertSendAndReceive("ProductExchange", "delete", product);
        }catch (Exception err){
            return false;
        }
    }

    @GetMapping (value = "/getByName/{productName}")
    public Product serviceGetProductByName(@PathVariable("productName") String productName){
        try{
            return (Product) this.rabbitTemplate.convertSendAndReceive("ProductExchange", "getname", productName);
        } catch (Exception err){
            return null;
        }
    }

    @GetMapping (value = "/getAllProduct")
    public ArrayList<Product> serviceGetAllProduct(){
        try{
            return (ArrayList<Product>) this.rabbitTemplate.convertSendAndReceive("ProductExchange", "getall", "");
        } catch (Exception err){
            return null;
        }
    }

}
