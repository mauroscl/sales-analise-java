package br.com.mauroscl.sales.application;

import br.com.mauroscl.sales.domain.*;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SalesCsvProcessor implements Processor {

    private ICsvSaleService csvSaleService;
    private ISaleStatisticsService saleStatisticsService;

    @Autowired
    public SalesCsvProcessor(final ICsvSaleService csvSaleService, final ISaleStatisticsService saleStatisticsService) {
        this.csvSaleService = csvSaleService;
        this.saleStatisticsService = saleStatisticsService;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        var rows = (List<List<String>>) exchange.getIn().getBody();

        SaleContext saleContext = csvSaleService.loadContext(rows);

        SaleSummary saleSummary = new SaleSummary();
        saleSummary.setAmountSalesman(saleContext.getAmountSalesman());
        saleSummary.setAmountCustomer(saleContext.getAmountCustomer());
        saleSummary.setMostExpensiveSales(saleStatisticsService.getMostExpensiveSales(saleContext.getSales()));
        saleSummary.setWorstSellers(saleStatisticsService.getWorstSellers(saleContext.getSales()));

        exchange.getOut().setBody(saleSummary);

        //replicate header CamelFileName because it is necessary to determine the name of done file.
        exchange.getOut().setHeader(Exchange.FILE_NAME, exchange.getIn().getHeader(Exchange.FILE_NAME));
    }

}
