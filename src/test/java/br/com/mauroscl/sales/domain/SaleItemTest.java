package br.com.mauroscl.sales.domain;

import org.junit.Assert;
import org.junit.Test;

public class SaleItemTest {

    @Test
    public void mustCalculateTotalOfItem(){
        SaleItem saleItem = new SaleItem();
        saleItem.setPrice(10);
        saleItem.setQuantity(4);
        Assert.assertTrue(Double.valueOf(40).equals(saleItem.getTotal()));
    }

}