package br.com.mauroscl.sales.route;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

public class CounterAgrregateStrategy implements AggregationStrategy {
    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        if (oldExchange == null) {
            newExchange.getIn().setBody(1L);
            return newExchange;
        }
        Long counter = oldExchange.getIn().getBody(Long.class);
        counter ++;
        oldExchange.getIn().setBody(counter);
        return oldExchange;
    }
}
