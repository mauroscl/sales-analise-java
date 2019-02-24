package br.com.mauroscl.sales.domain;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class SaleTest {

    @Test
    public void mustCalculateTotalOfSale() {
        Sale sale = new Sale();

        SaleItem saleItem1 = new SaleItem();
        saleItem1.setQuantity(10);
        saleItem1.setPrice(50.2);

        SaleItem saleItem2 = new SaleItem();
        saleItem2.setQuantity(3);
        saleItem2.setPrice(30.5);

        sale.setItems(List.of(saleItem1, saleItem2));

        Assert.assertTrue(sale.getTotal().equals(593.5) );
    }
}