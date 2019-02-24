package br.com.mauroscl.sales.infra;

import br.com.mauroscl.sales.domain.Sale;
import br.com.mauroscl.sales.infra.SaleProcessor;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

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