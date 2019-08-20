package br.com.mauroscl.sales.application.ports.driven;

import br.com.mauroscl.sales.application.domain.SaleContext;

public interface SaleContextLoader {
    SaleContext loadContext(final Object saleContent);
}
