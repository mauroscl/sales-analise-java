package br.com.mauroscl.sales.route;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class FileProcessRouteTest {

    @Autowired
    private ProducerTemplate producerTemplate;

    @EndpointInject(uri = "mock:file:data/in")
    private MockEndpoint mockInputFile;

    @EndpointInject(uri = "mock:file:data/out")
    private MockEndpoint mockOutputFile;

    @Test
    public void mustProcessFile() throws Exception {

        //mockInputFile.createConsumer(exchange -> exchange.getIn().setBody(""));

    }

}