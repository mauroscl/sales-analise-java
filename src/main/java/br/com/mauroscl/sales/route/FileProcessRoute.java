package br.com.mauroscl.sales.route;

import org.apache.camel.Exchange;
import org.apache.camel.Expression;
import org.apache.camel.Header;
import org.apache.camel.Headers;
import org.apache.camel.builder.AggregationStrategyClause;
import org.apache.camel.builder.ExpressionClause;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.DataFormatDefinition;
import org.apache.camel.model.dataformat.CsvDataFormat;
import org.apache.camel.spi.DataFormat;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.OutputStream;

@Component
public class FileProcessRoute extends RouteBuilder {

    private static final String DIRECT_CONTENT_0 = "direct:content0";
    private static final String DIRECT_CONTENT_1 = "direct:content1";

    @Override
    public void configure() throws Exception {
        from("file:data/in?include=.*.dat")
                //.log(simple(header(Exchange.FILE_NAME))))
                .log("Processando arquivo: ${file:name}")
                .unmarshal(new CsvDataFormat("ç"))
                .process(new SalesCsvProcessor())
                .log("novo arquivo: ${file:name.noext}.done.${file:ext}")
                .marshal().string()
                //.setHeader(Exchange.FILE_NAME, simple("${}"))
                .to("file:data/out");
                //.to("file:data/out?fileName=processado.dat");

        from(DIRECT_CONTENT_0, DIRECT_CONTENT_1)
                .log("RECEBENDO NO ENDPOINT")
                .log("${in.header.fileName}")
                .log("${body}");

    }
}
