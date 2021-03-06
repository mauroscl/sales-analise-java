package br.com.mauroscl.sales.adapters.secondary;

import br.com.mauroscl.sales.application.domain.Sale;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class SaleProcessorTest {

    @Test
    public void mustProcessSaleLine() throws Exception {
        CsvSaleDeserializer saleProcessor = new CsvSaleDeserializer();
        Sale sale = saleProcessor.deserializeSale(List.of("003", "10", "[1-10-100,2-30-2.50,3-40-3.10]", "Diego")).get();
        assertEquals("10", sale.getId());
        assertEquals("Diego", sale.getSalesman());
        assertEquals(3, sale.getItems().size());
        assertEquals(Double.valueOf(1199), sale.getTotal());
    }

    @Test
    public void mustIgnoreSaleRowWithBadFormat() {
        CsvSaleDeserializer saleProcessor = new CsvSaleDeserializer();
        assertFalse(saleProcessor.deserializeSale(List.of("003", "[1-10-100,2-30-2.50,3-40-3.10]", "Diego")).isPresent());
   }

    @Test
    public void mustIgnoreSaleRowWithInvalidItems() {
        CsvSaleDeserializer saleProcessor = new CsvSaleDeserializer();
        assertFalse( saleProcessor.deserializeSale(List.of("003", "10", "[1-A-100,2-30-2.50,3-40-3.10]", "Diego")).isPresent());
    }
}