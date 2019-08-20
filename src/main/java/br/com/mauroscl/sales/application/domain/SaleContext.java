package br.com.mauroscl.sales.application.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public class SaleContext {
    private int amountSalesman;
    private int amountCustomer;
    private List<Sale> sales;

    private SaleContext() {
        sales = new ArrayList<>();
    }
    public static SaleContext emptyContext() {
        return new SaleContext();
    }

    public List<String> getMostExpensiveSales() {
        if (sales.isEmpty()){
            return Collections.emptyList();
        }

        Double maxSaleValue = Collections.max(sales.stream().map(Sale::getTotal).collect(Collectors.toList()));

        return sales.stream()
                .filter(sale -> sale.getTotal().equals(maxSaleValue))
                .map(Sale::getId)
                .collect(Collectors.toList());
    }

    public List<String> getWorstSellers() {
        if (sales.isEmpty()){
            return Collections.emptyList();
        }

        Map<String, Double> salesmanByTotal = sales.stream()
                .collect(Collectors.groupingBy(Sale::getSalesman, Collectors.summingDouble(Sale::getTotal)));
        Double minSaleValueBySalesman = Collections.min(salesmanByTotal.values());

        return salesmanByTotal.entrySet()
                .stream()
                .filter(item -> item.getValue().equals(minSaleValueBySalesman))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
