package br.com.mauroscl.sales.infra;

import br.com.mauroscl.sales.domain.Sale;
import br.com.mauroscl.sales.domain.SaleItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class SaleProcessor {

    private static final String ITEMS_DELIMITER = ",";
    private static final String VALUES_DELIMITER = "-";

    private static final int SALE_ID_INDEX = 1;
    private static final int SALES_ITENS_INDEX = 2;
    private static final int SALESMAN_INDEX = 3;

    private static final int SALEITEM_ID_INDEX = 0;
    private static final int SALEITEM_QUANTITY_INDEX = 1;
    private static final int SALEITEM_PRICE_INDEX = 2;

    private static final String INVALID_ROW_MESSAGE = "Invalid sale row";

    private static final Logger logger = LogManager.getLogger(SaleProcessor.class);

    public Optional<Sale> processSale(final List<String> saleRow) {

        if (saleRow.size() != 4) {
            logInvalidRow(saleRow);
            return Optional.empty();
        }

        try {
            Sale sale = new Sale();
            sale.setId(saleRow.get(SALE_ID_INDEX));
            sale.setSalesman(saleRow.get(SALESMAN_INDEX));

            String serializedSaleItens = saleRow.get(SALES_ITENS_INDEX);
            List<SaleItem> saleItems = processItens(serializedSaleItens);
            sale.setItems(saleItems);

            return Optional.of(sale) ;
        } catch (Exception e) {
            logInvalidRow(saleRow);
            logger.warn(e.getMessage());
            return Optional.empty();
        }
    }

    private List<SaleItem> processItens(final String serializedSaleItens) {
        var itens = new ArrayList<SaleItem>();

        String[] splitedItens = serializedSaleItens
                .substring(1, serializedSaleItens.length() - 1)
                .split(ITEMS_DELIMITER);
        for (int i = 0; i < splitedItens.length; i++) {
            itens.add(processItem(splitedItens[i]));
        }
        return itens;
    }

    private SaleItem processItem(final String serializedItem) {
        String[] splitedValues = serializedItem.split(VALUES_DELIMITER);
        SaleItem saleItem = new SaleItem();
        saleItem.setId(splitedValues[SALEITEM_ID_INDEX]);
        saleItem.setQuantity(Double.valueOf(splitedValues[SALEITEM_QUANTITY_INDEX]));
        saleItem.setPrice(Double.valueOf(splitedValues[SALEITEM_PRICE_INDEX]));
        return saleItem;
    }

    private void logInvalidRow(List<String> saleRow){
        logger.warn(INVALID_ROW_MESSAGE + ": " + saleRow);
    }

}
