package br.com.mauroscl.sales.application.domain;

import org.junit.Assert;
import org.junit.Test;

public class SaleItemTest {

    @Test
    public void mustCalculateTotalOfItem(){
        SaleItem saleItem = new SaleItem("1", 4, 10);
        Assert.assertTrue(Double.valueOf(40).equals(saleItem.getTotal()));
    }

}