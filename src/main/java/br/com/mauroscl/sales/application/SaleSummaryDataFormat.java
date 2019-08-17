package br.com.mauroscl.sales.application;

import br.com.mauroscl.sales.domain.SaleSummary;
import org.apache.camel.Exchange;
import org.apache.camel.spi.DataFormat;

import java.io.InputStream;
import java.io.OutputStream;

public class SaleSummaryDataFormat implements DataFormat {
    @Override
    public void marshal(final Exchange exchange, final Object graph, final OutputStream stream) throws Exception {
        SaleSummary saleSummary = exchange.getIn().getBody(SaleSummary.class);
        stream.write(convertToString(saleSummary).getBytes());
    }

    @Override
    public Object unmarshal(final Exchange exchange, final InputStream stream) throws Exception {
        return null;
    }

    private String convertToString(SaleSummary saleSummary) {
        return String.format("amountSalesman=%d\r\namountCustomer=%d\r\nworstSellers=%s\r\nmostExpensiveSales=%s",
                saleSummary.getAmountSalesman(),
                saleSummary.getAmountCustomer(),
                saleSummary.getWorstSellers(),
                saleSummary.getMostExpensiveSales());
    }
}
