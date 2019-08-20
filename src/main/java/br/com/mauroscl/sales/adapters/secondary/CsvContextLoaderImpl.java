package br.com.mauroscl.sales.adapters.secondary;

import br.com.mauroscl.sales.application.domain.Sale;
import br.com.mauroscl.sales.application.domain.SaleContext;
import br.com.mauroscl.sales.application.ports.driven.SaleContextLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
class CsvContextLoaderImpl implements SaleContextLoader {

    private static final String SALESMAN_PREFIX = "001";
    private static final String CUSTOMER_PREFIX = "002";
    private static final String SALES_PREFIX = "003";

    private final CsvSaleDeserializer saleDeserializer;

    CsvContextLoaderImpl(final CsvSaleDeserializer saleDeserializer) {
        this.saleDeserializer = saleDeserializer;
    }

    @Override
    public SaleContext loadContext(final Object saleContent ) {

        final var csvRows = (List<List<String>>) saleContent;

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
                    saleDeserializer.deserializeSale(row)
                            .ifPresent(sales::add);
                    break;
                default:
                    log.warn("Invalid record prefix: {}. This record will be ignored: {}", rowType, row);
            }
        }

        return new SaleContext(amountSalesman, amountCustomer, sales);
    }
}
