package br.com.mauroscl.sales.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.CsvDataFormat;
import org.springframework.stereotype.Component;

@Component
public class FileProcessRoute extends RouteBuilder {

    @Override
    public void configure() {
        from("file:data/in?include=.*.dat")
                .log("Processando arquivo: ${file:name}")
                .unmarshal(new CsvDataFormat("ç"))
                .process(new SalesCsvProcessor())
                .marshal().string()
                .to("file:data/out?fileName=${file:name.noext}.done.${file:ext}");
    }
}
