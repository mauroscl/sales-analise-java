package br.com.mauroscl.sales.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class SaleSummary {
    private int amountSalesman;
    private int amountCustomer;
    private List<String> worstSellers;
    private List<String> mostExpensiveSales;

}
