package com.example.sop_midterm_63070118.pojo;

import com.vaadin.flow.component.template.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document("Product")
public class Product implements Serializable {
    @Id
    private String id;
    private String productName;
    private Double productCost, productProfit, productPrice;

    public Product() {
    }

    public Product(String id, String productName, Double productCost, Double productProfit, Double productPrice) {
        this.id = id;
        this.productName = productName;
        this.productCost = productCost;
        this.productProfit = productProfit;
        this.productPrice = productPrice;
    }

    public String getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public Double getProductCost() {
        return productCost;
    }

    public Double getProductProfit() {
        return productProfit;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductCost(Double productCost) {
        this.productCost = productCost;
    }

    public void setProductProfit(Double productProfit) {
        this.productProfit = productProfit;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }
}
