package br.com.mauroscl.sales.domain;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class SaleTest {

    @Test
    public void mustCalculateTotalOfSale() {
        Sale sale = new Sale("1", "Mauro");

        SaleItem saleItem1 = new SaleItem("1", 10, 50.2);

        SaleItem saleItem2 = new SaleItem("2", 3, 30.5);

        sale.addItems(List.of(saleItem1, saleItem2));

        Assert.assertTrue(sale.getTotal().equals(593.5) );
    }
}