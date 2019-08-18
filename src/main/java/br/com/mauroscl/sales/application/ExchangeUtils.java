package br.com.mauroscl.sales.application;

import org.apache.camel.Exchange;

class ExchangeUtils {

    private ExchangeUtils(){}

    static void copyHeader(Exchange exchange, String headerName) {
        exchange.getOut().setHeader(headerName, exchange.getIn().getHeader(headerName));
    }
}
