package br.com.mauroscl.sales.application;

import br.com.mauroscl.sales.domain.ICsvSaleService;
import br.com.mauroscl.sales.domain.ISaleStatisticsService;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.CsvDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FileProcessRoute extends RouteBuilder {

    private ICsvSaleService csvSaleService;
    private ISaleStatisticsService saleAnalyser;

    public static final String FILE_PROCESS_ROUTE = "FILE_PROCESSOR";

    @Autowired
    public FileProcessRoute(final ICsvSaleService csvSaleService, final ISaleStatisticsService saleAnalyser) {
        this.csvSaleService = csvSaleService;
        this.saleAnalyser = saleAnalyser;
    }

    @Override
    public void configure() {

        onException(Exception.class)
                .to("log:?showException=true")
                .maximumRedeliveries(0)
                .handled(true);

        from("file:data/in?include=.*.dat")
                .routeId(FILE_PROCESS_ROUTE)
                .log("Processando arquivo: ${file:name}")
                .unmarshal(new CsvDataFormat("ç"))
                .process(new SalesCsvProcessor(csvSaleService, saleAnalyser))
                .marshal(new SaleSummaryDataFormat())
                .to("file:data/out?fileName=${file:name.noext}.done.${file:ext}");
    }
}
