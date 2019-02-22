package br.com.mauroscl.sales.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class FileProcessRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("file:data/in")
                .to("file:data/out");

    }
}
