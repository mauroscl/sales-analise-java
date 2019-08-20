package br.com.mauroscl.sales.adapters.secondary;

import br.com.mauroscl.sales.application.domain.SaleContext;
import br.com.mauroscl.sales.application.ports.driven.SaleContextLoader;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class CsvContextLoaderImplTest {

    @Test
    public void mustLoadSaleContext() {
        CsvSaleDeserializer saleProcessor = new CsvSaleDeserializer();
        SaleContextLoader csvSaleService = new CsvContextLoaderImpl(saleProcessor);

        var csvRows = List.of(
                List.of("001", "1234567891234", "Diego", "50000"),
                List.of("002", "2345675434544345", "Jose da Silva", "Rural"),
                List.of("003", "10", "[1-10-100,2-30-2.50,3-40-3.10]", "Diego")
        );

        SaleContext saleContext = csvSaleService.loadContext(csvRows);

        assertEquals(1, saleContext.getAmountSalesman());
        assertEquals(1, saleContext.getAmountCustomer());
        assertEquals(1, saleContext.getSales().size());

    }

}