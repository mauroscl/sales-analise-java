package br.com.mauroscl.sales.domain;

import java.util.List;

public interface ISaleAnalyser {
    List<String> getMostExpensiveSales(List<Sale> sales);

    List<String> getWorstSellers(List<Sale> sales);
}
