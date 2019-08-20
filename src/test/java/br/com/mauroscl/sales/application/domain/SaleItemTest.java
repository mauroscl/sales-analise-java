package br.com.mauroscl.sales.application.domain;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SaleItemTest {

    @Test
    public void mustCalculateTotalOfItem(){
        SaleItem saleItem = new SaleItem("1", 4, 10);
        assertEquals(40d, saleItem.getTotal(), 0.0);
    }

}