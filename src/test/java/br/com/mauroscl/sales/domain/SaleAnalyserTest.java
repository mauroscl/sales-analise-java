package br.com.mauroscl.sales.domain;

import br.com.mauroscl.sales.dataprovider.SalesDataProvider;
import br.com.mauroscl.sales.domain.Sale;
import br.com.mauroscl.sales.domain.SaleAnalyser;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SaleAnalyserTest {

    private SaleAnalyser saleAnalyser;

    public SaleAnalyserTest() {
        saleAnalyser = new SaleAnalyser();
    }

    @Test
    public void mustReturnEmptyListForWorstSellersWhenNoSalesAvailable() {
        assertTrue(saleAnalyser.getWorstSellers(Collections.emptyList()).isEmpty());
    }

    @Test
    public void mustReturnNameOfWorstSeller() {
        //1 sale with total 1040 and 1 sale with total 880
        List<Sale> sales = SalesDataProvider.getTwoSales();
        List<String> worstSellers = saleAnalyser.getWorstSellers(sales);
        assertEquals(1, worstSellers.size());
        assertTrue(worstSellers.contains("João"));
    }

    @Test
    public void mustReturnNamesOfWorstSellersWhenTied() {
        //two sales with total 500 and 1 sale with total 600
        List<Sale> sales = SalesDataProvider.generateWorstSellersTied();
        List<String> worstSellers = saleAnalyser.getWorstSellers(sales);
        assertEquals(2, worstSellers.size());
        assertTrue(worstSellers.contains("Mauro"));
        assertTrue(worstSellers.contains("João"));
    }

    @Test
    public void mustReturnEmptyListForMostExpensiveSalesWhenNoSailvesAvailable() {
        assertTrue(saleAnalyser.getMostExpensiveSales(Collections.emptyList()).isEmpty());
    }

    @Test
    public void mustReturnIdOfMostExpensiveSale() {
        //1 sale with total 1040 and 1 sale with total 880
        List<Sale> sales = SalesDataProvider.getTwoSales();
        List<String> mostExpensiveSales = saleAnalyser.getMostExpensiveSales(sales);
        assertEquals(1, mostExpensiveSales.size());
        assertTrue(mostExpensiveSales.contains("01"));
    }

    @Test
    public void mustReturnIdsOfMostExpensiveSalesWhenTied() {
        //1 sale with total 500 and two sales with total 900.
        List<Sale> sales = SalesDataProvider.generateMostExpensiveSalesTied();
        List<String> mostExpensiveSales = saleAnalyser.getMostExpensiveSales(sales);
        assertEquals(2, mostExpensiveSales.size());
        assertTrue(mostExpensiveSales.contains("02"));
        assertTrue(mostExpensiveSales.contains("03"));
    }

}
