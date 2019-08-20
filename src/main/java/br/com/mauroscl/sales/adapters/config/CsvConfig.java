package br.com.mauroscl.sales.adapters.config;

public class CsvConfig {

    private CsvConfig(){}

    public static final String COLUMN_DELIMITER = "ç";

    public static final String SALEITEMS_ITEMS_DELIMITER = ",";
    public static final String SALEITEMS_VALUES_DELIMITER = "-";

    public static final int SALE_COLUMNS = 4;
    public static final int SALE_ID_INDEX = 1;
    public static final int SALE_ITEMS_INDEX = 2;
    public static final int SALE_SALESMAN_INDEX = 3;

    public static final int SALEITEM_ID_INDEX = 0;
    public static final int SALEITEM_QUANTITY_INDEX = 1;
    public static final int SALEITEM_PRICE_INDEX = 2;
}
