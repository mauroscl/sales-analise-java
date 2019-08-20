package br.com.mauroscl.sales.application.domain;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SaleContextTest {

    @Test
    public void mustReturnEmptyListForWorstSellersWhenNoSalesAvailable() {
        SaleContext saleContext = SaleContext.emptyContext();
        assertTrue(saleContext.getWorstSellers().isEmpty());
    }

    @Test
    public void mustReturnNameOfWorstSeller() {
        //1 sale with total 1040 and 1 sale with total 880
        SaleContext saleContext = SaleContextDataProvider.getTwoSales();

        List<String> worstSellers = saleContext.getWorstSellers();
        assertEquals(1, worstSellers.size());
        assertTrue(worstSellers.contains("João"));
    }

    @Test
    public void mustReturnNamesOfWorstSellersWhenTied() {
        //two sales with total 500 and 1 sale with total 600
        SaleContext saleContext = SaleContextDataProvider.generateWorstSellersTied();

        List<String> worstSellers = saleContext.getWorstSellers();
        assertEquals(2, worstSellers.size());
        assertTrue(worstSellers.contains("Mauro"));
        assertTrue(worstSellers.contains("João"));
    }

    @Test
    public void mustReturnEmptyListForMostExpensiveSalesWhenNoSailesAvailable() {
        SaleContext saleContext = SaleContext.emptyContext();
        assertTrue(saleContext.getMostExpensiveSales().isEmpty());
    }

    @Test
    public void mustReturnIdOfMostExpensiveSale() {
        //1 sale with total 1040 and 1 sale with total 880
        SaleContext saleContext = SaleContextDataProvider.getTwoSales();

        List<String> mostExpensiveSales = saleContext.getMostExpensiveSales();
        assertEquals(1, mostExpensiveSales.size());
        assertTrue(mostExpensiveSales.contains("01"));
    }

    @Test
    public void mustReturnIdsOfMostExpensiveSalesWhenTied() {
        //1 sale with total 500 and two sales with total 900.
        SaleContext saleContext = SaleContextDataProvider.generateMostExpensiveSalesTied();
        List<String> mostExpensiveSales = saleContext.getMostExpensiveSales();
        assertEquals(2, mostExpensiveSales.size());
        assertTrue(mostExpensiveSales.contains("02"));
        assertTrue(mostExpensiveSales.contains("03"));
    }

}
