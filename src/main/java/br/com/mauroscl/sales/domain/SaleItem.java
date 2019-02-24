package br.com.mauroscl.sales.domain;

import lombok.Setter;

@Setter
public class SaleItem {
    private String id;
    private double quantity;
    private double price;
    public double getTotal() {
        return quantity * price;
    }
}
