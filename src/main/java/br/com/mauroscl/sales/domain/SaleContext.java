package br.com.mauroscl.sales.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class SaleContext {
    private int amountSalesman;
    private int amountCustomer;
    private List<Sale> sales;

}
