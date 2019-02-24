package br.com.mauroscl.sales.infra;

import br.com.mauroscl.sales.domain.ICsvSaleService;
import br.com.mauroscl.sales.domain.Sale;
import br.com.mauroscl.sales.domain.SaleContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CsvSaleService implements ICsvSaleService {

    private static final String SALESMAN_PREFIX = "001";
    private static final String CUSTOMER_PREFIX = "002";
    private static final String SALES_PREFIX = "003";

    @Override
    public SaleContext loadContext(final List<List<String>> csvRows) {
        int amountSalesman = 0;
        int amountCustomer = 0;
        List<Sale> sales = new ArrayList<>();
        SaleProcessor saleProcessor = new SaleProcessor();
        for(var row: csvRows){
            String rowType = row.get(0);
            switch (rowType){
                case SALESMAN_PREFIX:
                    amountSalesman++;
                    break;
                case CUSTOMER_PREFIX:
                    amountCustomer++;
                    break;
                case SALES_PREFIX:
                    Sale sale = null;
                    try {
                        sale = saleProcessor.processSale(row);
                        sales.add(sale);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        }

        SaleContext saleContext = new SaleContext(amountSalesman, amountCustomer, sales);
        return saleContext;
    }
}
