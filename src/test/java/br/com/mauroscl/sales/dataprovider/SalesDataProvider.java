package br.com.mauroscl.sales.dataprovider;

import br.com.mauroscl.sales.model.Sale;
import br.com.mauroscl.sales.model.SaleItem;

import java.util.List;

public class SalesDataProvider {

    public static List<Sale> getTwoSales() {
        Sale sale1 = new Sale();
        sale1.setId("01");
        sale1.setSalesman("Mauro");

        SaleItem saleItem1 = new SaleItem();
        saleItem1.setId("001");
        saleItem1.setQuantity(10);
        saleItem1.setPrice(50);

        SaleItem saleItem2 = new SaleItem();
        saleItem2.setId("002");
        saleItem2.setQuantity(3);
        saleItem1.setPrice(180);

        //total = 10*50 + 3*180 = 1040
        sale1.setItems(List.of(saleItem1, saleItem2));

        Sale sale2 = new Sale();
        sale2.setId("02");
        sale2.setSalesman("João");

        SaleItem saleItem3 = new SaleItem();
        saleItem1.setId("001");
        saleItem1.setQuantity(12);
        saleItem1.setPrice(45);

        SaleItem saleItem4 = new SaleItem();
        saleItem2.setId("002");
        saleItem2.setQuantity(10);
        saleItem1.setPrice(34);

        //total = 12*45 + 10*34 = 880
        sale2.setItems(List.of(saleItem3, saleItem4));

        return List.of(sale1, sale2);
    }

    public static List<Sale> generateWorstSellersTied() {
        Sale sale1 = new Sale();
        sale1.setId("01");
        sale1.setSalesman("Mauro");

        SaleItem saleItem1 = new SaleItem();
        saleItem1.setId("001");
        saleItem1.setQuantity(10);
        saleItem1.setPrice(50);
        //total = 500
        sale1.setItems(List.of(saleItem1));

        Sale sale2 = new Sale();
        sale2.setId("02");
        sale2.setSalesman("João");

        SaleItem saleItem2 = new SaleItem();
        saleItem2.setId("002");
        saleItem2.setQuantity(2);
        saleItem2.setPrice(250);
        //total = 500
        sale2.setItems(List.of(saleItem2));

        Sale sale3 = new Sale();
        sale3.setId("03");
        sale3.setSalesman("Paulo");

        SaleItem saleItem3 = new SaleItem();
        saleItem3.setId("003");
        saleItem3.setQuantity(2);
        saleItem3.setPrice(300);
        //total = 600
        sale3.setItems(List.of(saleItem3));

        return List.of(sale1, sale2, sale3);
    }

    public static List<Sale> generateMostExpensiveSalesTied() {
        Sale sale1 = new Sale();
        sale1.setId("01");
        sale1.setSalesman("Mauro");

        SaleItem saleItem1 = new SaleItem();
        saleItem1.setId("001");
        saleItem1.setQuantity(10);
        saleItem1.setPrice(50);
        //total = 500
        sale1.setItems(List.of(saleItem1));

        Sale sale2 = new Sale();
        sale2.setId("02");
        sale2.setSalesman("João");

        SaleItem saleItem2 = new SaleItem();
        saleItem2.setId("002");
        saleItem2.setQuantity(3);
        saleItem2.setPrice(300);
        //total = 900
        sale2.setItems(List.of(saleItem2));

        Sale sale3 = new Sale();
        sale3.setId("03");
        sale3.setSalesman("Paulo");

        SaleItem saleItem3 = new SaleItem();
        saleItem3.setId("003");
        saleItem3.setQuantity(2);
        saleItem3.setPrice(450);
        //total = 900
        sale3.setItems(List.of(saleItem3));

        return List.of(sale1, sale2, sale3);
    }

}
