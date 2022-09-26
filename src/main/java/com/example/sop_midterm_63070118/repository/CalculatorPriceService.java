package com.example.sop_midterm_63070118.repository;

import org.springframework.stereotype.Service;

@Service
public class CalculatorPriceService {

    public CalculatorPriceService() {
    }

    public double getPrice(double cost, double profit){
        return cost + profit;
    }
}
