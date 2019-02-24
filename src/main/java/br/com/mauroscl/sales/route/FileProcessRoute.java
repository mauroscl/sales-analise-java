package br.com.mauroscl.sales.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.CsvDataFormat;
import org.springframework.stereotype.Component;

@Component
public class FileProcessRoute extends RouteBuilder {

    public static final String FILE_PROCESS_ROUTE = "FILE_PROCESSOR";
    @Override
    public void configure() {
        from("file:data/in?include=.*.dat")
                .routeId(FILE_PROCESS_ROUTE)
                .log("Processando arquivo: ${file:name}")
                .unmarshal(new CsvDataFormat("ç"))
                .process(new SalesCsvProcessor())
                .marshal().string()
                .to("file:data/out?fileName=${file:name.noext}.done.${file:ext}");
    }
}
