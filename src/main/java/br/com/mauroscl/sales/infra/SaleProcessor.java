package br.com.mauroscl.sales.infra;

import br.com.mauroscl.sales.domain.Sale;
import br.com.mauroscl.sales.domain.SaleItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class SaleProcessor {

    private static final String ITEMS_DELIMITER = ",";
    private static final String VALUES_DELIMITER = "-";

    private static final int SALE_ID_INDEX = 1;
    private static final int SALES_ITEMS_INDEX = 2;
    private static final int SALESMAN_INDEX = 3;

    private static final int SALEITEM_ID_INDEX = 0;
    private static final int SALEITEM_QUANTITY_INDEX = 1;
    private static final int SALEITEM_PRICE_INDEX = 2;

    private static final int EXPECTED_SALE_COLUMNS = 4;

    public Optional<Sale> processSale(final List<String> saleRow) {

        if (saleRow.size() != EXPECTED_SALE_COLUMNS) {
            log.warn("Invalid number of columns. This record will be ignored - actual: {} - expected: {}, row: {}",
                    saleRow.size(), EXPECTED_SALE_COLUMNS, saleRow);
            return Optional.empty();
        }

        try {
            String saleId = saleRow.get(SALE_ID_INDEX);
            String salesman = saleRow.get(SALESMAN_INDEX);
            Sale sale = new Sale(saleId, salesman);

            String serializedSaleItems = saleRow.get(SALES_ITEMS_INDEX);
            List<SaleItem> saleItems = processItens(serializedSaleItems);
            sale.addItems(saleItems);

            return Optional.of(sale);
        } catch (Exception e) {
            log.warn(String.format("invalid row format: %s", saleRow.toString()) , e);
            return Optional.empty();
        }
    }

    private List<SaleItem> processItens(final String serializedSaleItems) {
        var items = new ArrayList<SaleItem>();

        String[] splitedItems = serializedSaleItems
                .substring(1, serializedSaleItems.length() - 1)
                .split(ITEMS_DELIMITER);
        for (final String splitedItem : splitedItems) {
            items.add(processItem(splitedItem));
        }
        return items;
    }

    private SaleItem processItem(final String serializedItem) {
        String[] splitedValues = serializedItem.split(VALUES_DELIMITER);

        String itemId = splitedValues[SALEITEM_ID_INDEX];
        double quantity = Double.parseDouble(splitedValues[SALEITEM_QUANTITY_INDEX]);
        double price = Double.parseDouble(splitedValues[SALEITEM_PRICE_INDEX]);

        return new SaleItem(itemId, quantity, price);
    }

}
