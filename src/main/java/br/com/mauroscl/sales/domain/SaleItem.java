package br.com.mauroscl.sales.domain;

public class SaleItem {
    private String id;
    private double quantity;
    private double price;
    public double getTotal() {
        return quantity * price;
    }

    public SaleItem(final String id, final double quantity, final double price) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
    }
}
