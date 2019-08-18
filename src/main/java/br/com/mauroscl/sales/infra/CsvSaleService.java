package br.com.mauroscl.sales.infra;

import br.com.mauroscl.sales.domain.ICsvSaleService;
import br.com.mauroscl.sales.domain.Sale;
import br.com.mauroscl.sales.domain.SaleContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class CsvSaleService implements ICsvSaleService {

    private static final String SALESMAN_PREFIX = "001";
    private static final String CUSTOMER_PREFIX = "002";
    private static final String SALES_PREFIX = "003";

    private final SaleProcessor saleProcessor;

    public CsvSaleService(final SaleProcessor saleProcessor) {
        this.saleProcessor = saleProcessor;
    }

    @Override
    public SaleContext loadContext(final List<List<String>> csvRows) {
        int amountSalesman = 0;
        int amountCustomer = 0;
        List<Sale> sales = new ArrayList<>();
        for (var row : csvRows) {
            String rowType = row.get(0);
            switch (rowType) {
                case SALESMAN_PREFIX:
                    amountSalesman++;
                    break;
                case CUSTOMER_PREFIX:
                    amountCustomer++;
                    break;
                case SALES_PREFIX:
                    saleProcessor.processSale(row)
                            .ifPresent(sales::add);
                    break;
                default:
                    log.warn("Invalid record prefix: {}. This record will be ignored: {}", rowType, row);
            }
        }

        return new SaleContext(amountSalesman, amountCustomer, sales);
    }
}
