package br.com.mauroscl.sales.dataprovider;

import br.com.mauroscl.sales.domain.Sale;
import br.com.mauroscl.sales.domain.SaleItem;

import java.util.List;

public class SalesDataProvider {

    public static List<Sale> getTwoSales() {
        Sale sale1 = new Sale("01", "Mauro");

        SaleItem saleItem1 = new SaleItem("001",10, 50);

        SaleItem saleItem2 = new SaleItem("002", 3, 180);

        //total = 10*50 + 3*180 = 1040
        sale1.addItems(List.of(saleItem1, saleItem2));

        Sale sale2 = new Sale("02", "João");

        SaleItem saleItem3 = new SaleItem("001", 12, 45);

        SaleItem saleItem4 = new SaleItem("002", 10, 34);

        //total = 12*45 + 10*34 = 880
        sale2.addItems(List.of(saleItem3, saleItem4));

        return List.of(sale1, sale2);
    }

    public static List<Sale> generateWorstSellersTied() {
        Sale sale1 = new Sale("01", "Mauro");

        SaleItem saleItem1 = new SaleItem("001",10, 50);
        //total = 500
        sale1.addItems(List.of(saleItem1));

        Sale sale2 = new Sale("02", "João");

        SaleItem saleItem2 = new SaleItem("002", 2, 250);
        //total = 500
        sale2.addItems(List.of(saleItem2));

        Sale sale3 = new Sale("03", "Paulo");

        SaleItem saleItem3 = new SaleItem("003", 2, 300);
        //total = 600
        sale3.addItems(List.of(saleItem3));

        return List.of(sale1, sale2, sale3);
    }

    public static List<Sale> generateMostExpensiveSalesTied() {
        Sale sale1 = new Sale("01", "Mauro");

    SaleItem saleItem1 = new SaleItem("001", 10, 50);
        //total = 500
        sale1.addItems(List.of(saleItem1));

        Sale sale2 = new Sale("02", "João");

        SaleItem saleItem2 = new SaleItem("002", 3, 300);
        //total = 900
        sale2.addItems(List.of(saleItem2));

        Sale sale3 = new Sale("03", "Paulo");

        SaleItem saleItem3 = new SaleItem("003", 2, 450);
        //total = 900
        sale3.addItems(List.of(saleItem3));

        return List.of(sale1, sale2, sale3);
    }

}
