package br.com.mauroscl.sales.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SaleItem {
    private String id;
    private double quantity;
    private double price;
    public double getTotal() {
        return quantity * price;
    }
}
