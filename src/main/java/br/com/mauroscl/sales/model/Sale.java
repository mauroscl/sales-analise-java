package br.com.mauroscl.sales.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class Sale {
    private String id;
    private String salesman;
    private List<SaleItem> items;

    public Sale() {
        this.items = new ArrayList<>();
    }

    public Double getTotal() {
        return items.stream()
                .mapToDouble(item -> item.getTotal())
                .sum();
    }

    public List<SaleItem> getItems() {
        return Collections.unmodifiableList(this.getItems());
    }
}
