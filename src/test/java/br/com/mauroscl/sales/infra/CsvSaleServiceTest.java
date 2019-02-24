package br.com.mauroscl.sales.infra;

import br.com.mauroscl.sales.domain.ICsvSaleService;
import br.com.mauroscl.sales.domain.SaleContext;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class CsvSaleServiceTest {

    @Test
    public void mustLoadSaleContext() throws Exception {
        ICsvSaleService csvSaleService = new CsvSaleService();

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