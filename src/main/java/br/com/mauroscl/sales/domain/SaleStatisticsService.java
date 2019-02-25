package br.com.mauroscl.sales.domain;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class SaleStatisticsService implements ISaleStatisticsService {
    @Override
    public List<String> getMostExpensiveSales(List<Sale> sales) {
        if (sales.isEmpty()){
            return Collections.emptyList();
        }

        Double maxSaleValue = Collections.max(sales.stream().map(sale -> sale.getTotal()).collect(Collectors.toList()));

        return sales.stream()
                .filter(sale -> sale.getTotal().equals(maxSaleValue))
                .map(sale -> sale.getId())
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getWorstSellers(List<Sale> sales) {
        if (sales.isEmpty()){
            return Collections.emptyList();
        }

        Map<String, Double> salesmanByTotal = sales.stream()
                .collect(Collectors.groupingBy(sale -> sale.getSalesman(), Collectors.summingDouble(sale -> sale.getTotal())));
        Double minSaleValueBySalesman = Collections.min(salesmanByTotal.values());

        return salesmanByTotal.entrySet()
                .stream()
                .filter(item -> item.getValue().equals(minSaleValueBySalesman))
                .map(item -> item.getKey())
                .collect(Collectors.toList());
    }
}
