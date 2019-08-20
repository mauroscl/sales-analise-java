package br.com.mauroscl.sales.application.usecases;

import br.com.mauroscl.sales.application.domain.SaleContext;
import br.com.mauroscl.sales.application.domain.SaleSummary;
import br.com.mauroscl.sales.application.ports.driven.SaleContextLoader;
import br.com.mauroscl.sales.application.ports.driver.SaleStatisticsService;
import org.springframework.stereotype.Component;

@Component
class SaleStatisticsServiceImpl implements SaleStatisticsService {

    private final SaleContextLoader saleContextLoader;

    SaleStatisticsServiceImpl(final SaleContextLoader saleContextLoader) {
        this.saleContextLoader = saleContextLoader;
    }

    @Override
    public SaleSummary calculateStatistics(final Object saleContent) {
        SaleContext saleContext = saleContextLoader.loadContext(saleContent);
        return SaleSummary.builder()
                .amountSalesman(saleContext.getAmountSalesman())
                .amountCustomer(saleContext.getAmountCustomer())
                .mostExpensiveSales(saleContext.getMostExpensiveSales())
                .worstSellers(saleContext.getWorstSellers())
                .build();
    }
}
