package br.com.mauroscl.sales.application.domain;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class Sale {
    private String id;
    private String salesman;
    private List<SaleItem> items;

    public Sale(final String id, final String salesman) {
        this.id = id;
        this.salesman = salesman;
        this.items = new ArrayList<>();
    }

    public Double getTotal() {
        return items.stream()
                .mapToDouble(SaleItem::getTotal)
                .sum();
    }

    public List<SaleItem> getItems() {
        return Collections.unmodifiableList(this.items);
    }

    public void addItems(final List<SaleItem> saleItems) {
        this.items = saleItems;
    }
}
