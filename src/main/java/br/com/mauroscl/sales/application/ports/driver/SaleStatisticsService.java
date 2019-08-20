package br.com.mauroscl.sales.application.ports.driver;

import br.com.mauroscl.sales.application.domain.SaleSummary;

public interface SaleStatisticsService {
    SaleSummary calculateStatistics(Object saleContent);
}
