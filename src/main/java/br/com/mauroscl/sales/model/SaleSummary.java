package br.com.mauroscl.sales.model;

import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@ToString
public class SaleSummary {
    private int totalSalesman;
    private int totalCustomer;
    private List<String> worstSellers;
    private List<String> mostExpensiveSales;
}
