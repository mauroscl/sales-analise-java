package br.com.mauroscl.sales.application.domain;

import java.util.List;

public class SaleContextDataProvider {

    public static SaleContext getTwoSales() {
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

        return new SaleContext(2, 1, List.of(sale1, sale2));
    }

    public static SaleContext generateWorstSellersTied() {
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

        return new SaleContext(3, 1, List.of(sale1, sale2, sale3));
    }

    public static SaleContext generateMostExpensiveSalesTied() {
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

        return new SaleContext(3, 1, List.of(sale1, sale2, sale3));

    }

}
