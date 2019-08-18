package br.com.mauroscl.sales.domain;

import java.util.List;

public interface ICsvSaleService {
    SaleContext loadContext(List<List<String>> csvRows);
}
