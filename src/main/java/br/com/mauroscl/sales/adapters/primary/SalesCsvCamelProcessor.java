package br.com.mauroscl.sales.adapters.primary;

import br.com.mauroscl.sales.application.domain.SaleSummary;
import br.com.mauroscl.sales.application.ports.driver.SaleStatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
class SalesCsvCamelProcessor implements Processor {

    private SaleStatisticsService saleStatisticsService;

    @Autowired
    public SalesCsvCamelProcessor(final SaleStatisticsService saleStatisticsService) {
        this.saleStatisticsService = saleStatisticsService;
    }

    @Override
    public void process(Exchange exchange) {

        log.info("File: {} Thread Id: {} - Thread Name: {}", exchange.getIn().getHeader(Exchange.FILE_NAME),
                Thread.currentThread().getId(), Thread.currentThread().getName());

        SaleSummary saleSummary = saleStatisticsService.calculateStatistics(exchange.getIn().getBody());

        exchange.getOut().setBody(saleSummary);

        //replicate header CamelFileName because it is necessary to determine the name of done file.
        ExchangeUtils.copyHeader(exchange, Exchange.FILE_NAME);

    }

}
