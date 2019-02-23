package br.com.mauroscl.sales.route;

import br.com.mauroscl.sales.model.Sale;
import br.com.mauroscl.sales.model.SaleSummary;
import br.com.mauroscl.sales.service.SaleAnalyser;
import br.com.mauroscl.sales.service.SaleProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.ArrayList;
import java.util.List;


public class SalesCsvProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        int countSalesman = 0;
        int countCustomer = 0;
        var rows = (List<List<String>>) exchange.getIn().getBody();
        List<Sale> sales = new ArrayList<>();
        SaleProcessor saleProcessor = new SaleProcessor();
        for(var row: rows){
            String rowType = row.get(0);
            switch (rowType){
                case "001":
                    countSalesman++;
                    break;
                case "002":
                    countCustomer++;
                    break;
                case "003":
                    Sale sale = saleProcessor.processSale(row);
                    sales.add(sale);
            }
        }

        SaleAnalyser saleAnalyser = new SaleAnalyser();

        SaleSummary saleSummary = new SaleSummary();
        saleSummary.setAmountSalesman(countSalesman);
        saleSummary.setAmountCustomer(countCustomer);
        saleSummary.setMostExpensiveSales(saleAnalyser.getMostExpensiveSales(sales));
        saleSummary.setWorstSellers(saleAnalyser.getWorstSellers(sales));

        exchange.getOut().setBody(saleSummary);

        //replicate header CamelFileName because it is necessary to determine the name of done file.
        exchange.getOut().setHeader(Exchange.FILE_NAME, exchange.getIn().getHeader(Exchange.FILE_NAME));
    }

}
