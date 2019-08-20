package br.com.mauroscl.sales.adapters.primary;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.CsvDataFormat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public
class FileProcessCamelRoute extends RouteBuilder {

    private static final String SEDA_BASE_URI = "hazelcast-seda:csvfile";
    public static final String SEDA_PROCESS_ROUTE = "SEDA_ROUTE";

    private final int concurrentConsumers;
    private final SalesCsvCamelProcessor salesCsvCamelProcessor;


    FileProcessCamelRoute(@Value("${concurrent-consumers}") final int concurrentConsumers,
                                 final SalesCsvCamelProcessor salesCsvCamelProcessor) {
        this.concurrentConsumers = concurrentConsumers;
        this.salesCsvCamelProcessor = salesCsvCamelProcessor;
    }

    @Override
    public void configure() {

        onException(Exception.class)
                .to("log:?showException=true")
                .maximumRedeliveries(0)
                .handled(true);

        from("file:data/in?include=.*.dat")
                .process(exchange -> {
                    //body must be converted to String for preventing errors when transferExchange=true
                    //because before this conversion the body is a GenericFile class and not the file content.
                    exchange.getOut().setBody(exchange.getIn().getBody(String.class));
                    //needs copy the header "Exchange.FILE_NAME" to be available in the next route
                    ExchangeUtils.copyHeader(exchange, Exchange.FILE_NAME);
                })
                //transferExchange must be set to true for "Exchange.FILE_NAME" header to be sent to the next route
                .to(SEDA_BASE_URI + "?transferExchange=true" );

        from(createSedaUriForConsumers(SEDA_BASE_URI, concurrentConsumers))
                .routeId(SEDA_PROCESS_ROUTE)
                .unmarshal(new CsvDataFormat("ç"))
                .process(salesCsvCamelProcessor)
                .marshal(new SaleSummaryDataFormat())
                .to("file:data/out?fileName=${file:name.noext}.done.${file:ext}")
        ;
    }

    private String createSedaUriForConsumers(String baseUri, int concurrentConsumers) {
        return baseUri + "?concurrentConsumers=" + concurrentConsumers;
    }
}
