package com.example.sop_midterm_63070118.controller;

import com.example.sop_midterm_63070118.repository.CalculatorPriceService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalculatorPriceController {
    @Autowired
    private CalculatorPriceService calculatorPriceService;


    @GetMapping(value = "/getPrice/{cost}/{profit}")
    public double serviceGetProduct(@PathVariable("cost") double cost, @PathVariable("profit") double profit){
        return this.calculatorPriceService.getPrice(cost, profit);
    }
}
