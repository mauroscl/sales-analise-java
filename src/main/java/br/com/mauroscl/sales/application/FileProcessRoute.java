package br.com.mauroscl.sales.application;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.CsvDataFormat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FileProcessRoute extends RouteBuilder {

    private final int concurrentConsumers;
    private final SalesCsvProcessor salesCsvProcessor;

    public static final String FILE_PROCESS_ROUTE = "FILE_ROUTE";
    private static final String SEDA_URI = "hazelcast-seda:csvfile";

    public FileProcessRoute(@Value("${concurrent-consumers}") final int concurrentConsumers,
                            final SalesCsvProcessor salesCsvProcessor) {
        this.concurrentConsumers = concurrentConsumers;
        this.salesCsvProcessor = salesCsvProcessor;
    }

    @Override
    public void configure() {

        onException(Exception.class)
                .to("log:?showException=true")
                .maximumRedeliveries(0)
                .handled(true);

        from("file:data/in?include=.*.dat&noop=true")
                .routeId(FILE_PROCESS_ROUTE)
                .process(exchange -> {
                    exchange.getOut().setBody(exchange.getIn().getBody(String.class));
                    exchange.getOut().setHeader(Exchange.FILE_NAME, exchange.getIn().getHeader(Exchange.FILE_NAME));
                })
                .to(SEDA_URI + "?transferExchange=true" );

        from(createSedaUriForConsumers(SEDA_URI, concurrentConsumers))
                .unmarshal(new CsvDataFormat("ç"))
                .process(salesCsvProcessor)
                .marshal(new SaleSummaryDataFormat())
                .to("file:data/out?fileName=${file:name.noext}.done.${file:ext}")
        ;
    }

    private String createSedaUriForConsumers(String baseUri, int concurrentConsumers) {
        return baseUri + "?concurrentConsumers=" + concurrentConsumers;
    }
}
