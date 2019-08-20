package br.com.mauroscl.sales.adapters.primary;

import org.apache.camel.Exchange;

class ExchangeUtils {

    private ExchangeUtils(){}

    static void copyHeader(Exchange exchange, String headerName) {
        exchange.getOut().setHeader(headerName, exchange.getIn().getHeader(headerName));
    }
}
