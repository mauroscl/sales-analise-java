package br.com.mauroscl.sales.model;

import lombok.Setter;

import java.util.List;

@Setter
public class SaleSummary {
    private int amountSalesman;
    private int amountCustomer;
    private List<String> worstSellers;
    private List<String> mostExpensiveSales;

    @Override
    public String toString() {
        return "amountSalesman=" + amountSalesman + "\r\n" +
                "amountCustomer=" + amountCustomer + "\r\n" +
                "worstSellers=" + worstSellers + "\r\n" +
                "mostExpensiveSales=" + mostExpensiveSales;
    }
}
