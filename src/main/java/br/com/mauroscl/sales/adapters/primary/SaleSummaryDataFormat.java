package br.com.mauroscl.sales.adapters.primary;

import br.com.mauroscl.sales.application.domain.SaleSummary;
import org.apache.camel.Exchange;
import org.apache.camel.spi.DataFormat;

import java.io.InputStream;
import java.io.OutputStream;

class SaleSummaryDataFormat implements DataFormat {
    @Override
    public void marshal(final Exchange exchange, final Object graph, final OutputStream stream) throws Exception {
        SaleSummary saleSummary = exchange.getIn().getBody(SaleSummary.class);
        stream.write(convertToString(saleSummary).getBytes());
    }

    @Override
    public Object unmarshal(final Exchange exchange, final InputStream stream) {
        throw new UnsupportedOperationException("unmarshal is not necessary yet.");
    }

    private String convertToString(SaleSummary saleSummary) {
        return String.format("amountSalesman=%d%namountCustomer=%d%nworstSellers=%s%nmostExpensiveSales=%s",
                saleSummary.getAmountSalesman(),
                saleSummary.getAmountCustomer(),
                saleSummary.getWorstSellers(),
                saleSummary.getMostExpensiveSales());
    }
}
