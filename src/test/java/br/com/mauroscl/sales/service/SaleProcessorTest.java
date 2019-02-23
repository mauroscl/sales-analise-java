package br.com.mauroscl.sales.service;

import br.com.mauroscl.sales.model.Sale;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;


public class SaleProcessorTest {

    @Test
    public void mustProcessSaleLine() {
        SaleProcessor saleProcessor = new SaleProcessor();
        Sale sale = saleProcessor.processSale(List.of("003", "10", "[1-10-100,2-30-2.50,3-40-3.10]", "Diego"));
        assertEquals("10", sale.getId());
        assertEquals("Diego", sale.getSalesman());
        assertEquals(3, sale.getItems().size());
        assertEquals(Double.valueOf(1199), sale.getTotal());
    }

}