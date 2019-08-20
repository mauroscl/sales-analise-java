package br.com.mauroscl.sales.adapters.secondary;

import br.com.mauroscl.sales.application.domain.Sale;
import br.com.mauroscl.sales.application.domain.SaleItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static br.com.mauroscl.sales.adapters.config.CsvConfig.*;

@Component
@Slf4j
class CsvSaleDeserializer {

    Optional<Sale> deserializeSale(final List<String> saleRow) {

        if (saleRow.size() != SALE_COLUMNS) {
            log.warn("Invalid number of columns. This record will be ignored - actual: {} - expected: {}, row: {}",
                    saleRow.size(), SALE_COLUMNS, saleRow);
            return Optional.empty();
        }

        try {
            String saleId = saleRow.get(SALE_ID_INDEX);
            String salesman = saleRow.get(SALE_SALESMAN_INDEX);
            Sale sale = new Sale(saleId, salesman);

            String serializedSaleItems = saleRow.get(SALE_ITEMS_INDEX);
            List<SaleItem> saleItems = deserializeItems(serializedSaleItems);
            sale.addItems(saleItems);

            return Optional.of(sale);
        } catch (Exception e) {
            log.warn(String.format("invalid row format: %s", saleRow.toString()) , e);
            return Optional.empty();
        }
    }

    private List<SaleItem> deserializeItems(final String serializedSaleItems) {
        var items = new ArrayList<SaleItem>();

        String[] splitedItems = serializedSaleItems
                .substring(1, serializedSaleItems.length() - 1)
                .split(SALEITEMS_ITEMS_DELIMITER);
        for (final String splitedItem : splitedItems) {
            items.add(deserializeItem(splitedItem));
        }
        return items;
    }

    private SaleItem deserializeItem(final String serializedItem) {
        String[] splitedValues = serializedItem.split(SALEITEMS_VALUES_DELIMITER);

        String itemId = splitedValues[SALEITEM_ID_INDEX];
        double quantity = Double.parseDouble(splitedValues[SALEITEM_QUANTITY_INDEX]);
        double price = Double.parseDouble(splitedValues[SALEITEM_PRICE_INDEX]);

        return new SaleItem(itemId, quantity, price);
    }

}
