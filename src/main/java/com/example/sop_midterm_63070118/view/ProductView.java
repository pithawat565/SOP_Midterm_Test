package com.example.sop_midterm_63070118.view;

import com.example.sop_midterm_63070118.pojo.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;


@Route(value = "/index")
public class ProductView extends VerticalLayout {
    private ComboBox<String> productList;
    private TextField productName;
    private NumberField productCost, productProfit, productPrice;
    private Button add, update, delete, clear;
    private HorizontalLayout btnContainer;
    private Notification notice;
    private ObjectMapper mapper;
    private ArrayList<String> names, tempNames;

    private Product currestProduct;

    public ProductView() {
        productList = new ComboBox<>("Product List");
        productList.setWidth("600px");

        productName = new TextField("Product Name:");
        productName.setWidth("600px");
        productName.setValue("");

        productCost = new NumberField("Product Cost:");
        productCost.setWidth("600px");
        productCost.setValue(0.0);

        productProfit = new NumberField("Product Profit:");
        productProfit.setWidth("600px");
        productProfit.setValue(0.0);

        productPrice = new NumberField("Product Price:");
        productPrice.setWidth("600px");
        productPrice.setValue(0.0);
        productPrice.setEnabled(false);

        add = new Button("Add Product");
        update = new Button("Update Product");
        delete = new Button("Delete Product");
        clear = new Button("Clear Product");

        btnContainer = new HorizontalLayout();
        btnContainer.add(add, update, delete, clear);

        this.add(productList, productName, productCost, productProfit, productPrice, btnContainer);

        notice = new Notification();
        names = new ArrayList<>();
        mapper = new ObjectMapper();

        productCost.addKeyPressListener(Key.ENTER, keyPressEvent -> {
            double cost = productCost.getValue();
            double profit = productProfit.getValue();
            double output = WebClient.create()
                    .get()
                    .uri("http://localhost:8080/getPrice/"+cost+"/"+profit)
                    .retrieve()
                    .bodyToMono(double.class)
                    .block();
            this.productPrice.setValue(output);
        });

        productProfit.addKeyPressListener(Key.ENTER, keyPressEvent -> {
            double cost = productCost.getValue();
            double profit = productProfit.getValue();
            double output = WebClient.create()
                    .get()
                    .uri("http://localhost:8080/getPrice/"+cost+"/"+profit)
                    .retrieve()
                    .bodyToMono(double.class)
                    .block();
            this.productPrice.setValue(output);
        });

        add.addClickListener(e -> {
            String name = productName.getValue();
            double cost = productCost.getValue();
            double profit = productProfit.getValue();

            double outputPrice = WebClient.create()
                    .get()
                    .uri("http://localhost:8080/getPrice/"+cost+"/"+profit)
                    .retrieve()
                    .bodyToMono(double.class)
                    .block();
            this.productPrice.setValue(outputPrice);

            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("productName", name);
            formData.add("productCost", cost+"");
            formData.add("productProfit", profit+"");
            formData.add("productPrice", outputPrice+"");

            boolean output = WebClient.create()
                    .post()
                    .uri("http://localhost:8080/addProduct")
                    .body(BodyInserters.fromFormData(formData))
                    .retrieve()
                    .bodyToMono(boolean.class)
                    .block();
            if(output){
                notice = Notification.show("Added");
                notice.setDuration(500);
            }
        });

        productList.addFocusListener(e -> {
           ArrayList<Product> output = WebClient.create()
                   .get()
                   .uri("http://localhost:8080/getAllProduct")
                   .retrieve()
                   .bodyToMono(ArrayList.class)
                   .block();
           tempNames = new ArrayList<>();
           for(int index = 0; index < output.size() ; index++){
               Product product = mapper.convertValue(output.get(index), Product.class);
               tempNames.add(product.getProductName());
           }
           productList.setItems(tempNames);
        });

        clear.addClickListener(e -> {
            productList.setValue(null);
        });

        update.addClickListener(e -> {
            String id = currestProduct.getId();
            String name = productName.getValue();
            double cost = productCost.getValue();
            double profit = productProfit.getValue();

            double outputPrice = WebClient.create()
                    .get()
                    .uri("http://localhost:8080/getPrice/"+cost+"/"+profit)
                    .retrieve()
                    .bodyToMono(double.class)
                    .block();
            this.productPrice.setValue(outputPrice);

            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("id", id);
            formData.add("productName", name);
            formData.add("productCost", cost+"");
            formData.add("productProfit", profit+"");
            formData.add("productPrice", outputPrice+"");

            boolean output = WebClient.create()
                    .post()
                    .uri("http://localhost:8080/updateProduct")
                    .body(BodyInserters.fromFormData(formData))
                    .retrieve()
                    .bodyToMono(boolean.class)
                    .block();

            if(output){
                notice = Notification.show("Updated");
                notice.setDuration(500);
            }
        });

        delete.addClickListener(e -> {
            String id = currestProduct.getId();
            String name = productName.getValue();
            String cost = productCost.getValue()+"";
            String profit = productProfit.getValue()+"";
            String price = productPrice.getValue()+"";

            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("id", id);
            formData.add("productName", name);
            formData.add("productCost", cost);
            formData.add("productProfit", profit);
            formData.add("productPrice", price);


            boolean output = WebClient.create()
                    .post()
                    .uri("http://localhost:8080/deleteProduct")
                    .body(BodyInserters.fromFormData(formData))
                    .retrieve()
                    .bodyToMono(boolean.class)
                    .block();
            if(output){
                notice = Notification.show("Deleted");
                notice.setDuration(500);
            }
        });

        productList.addValueChangeListener(e -> {
            String name = productList.getValue();
            if(!(name == null)){
                Product nowProduct = WebClient.create()
                        .get()
                        .uri("http://localhost:8080/getByName/"+name)
                        .retrieve()
                        .bodyToMono(Product.class)
                        .block();
                currestProduct = mapper.convertValue(nowProduct, Product.class);
                productName.setValue(currestProduct.getProductName());
                productCost.setValue(currestProduct.getProductCost());
                productProfit.setValue(currestProduct.getProductProfit());
                productPrice.setValue(currestProduct.getProductPrice());
            }else{
                productName.setValue("");
                productCost.setValue(0.0);
                productProfit.setValue(0.0);
                productPrice.setValue(0.0);
                currestProduct = new Product();
            }
        });
    }
}
