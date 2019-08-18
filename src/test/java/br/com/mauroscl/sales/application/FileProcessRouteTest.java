package br.com.mauroscl.sales.application;

import br.com.mauroscl.sales.SalesApplication;
import org.apache.camel.*;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.EnableRouteCoverage;
import org.apache.camel.test.spring.MockEndpoints;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static br.com.mauroscl.sales.application.FileProcessRoute.SEDA_PROCESS_ROUTE;


@RunWith(CamelSpringBootRunner.class)
@SpringBootTest(classes = SalesApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@EnableRouteCoverage
@MockEndpoints
@ActiveProfiles("test")
public class FileProcessRouteTest {

    private static final String URI_FILE_INPUT = "file:data/in";
    private static final String URI_FILE_RESULT = "file:data/out";
    private static final String MOCK_URI_FILE_RESULT = "mock:" + URI_FILE_RESULT;

    @Autowired
    private CamelContext camelContext;

    @Produce
    private ProducerTemplate producerTemplate;

    @EndpointInject(uri = MOCK_URI_FILE_RESULT)
    private MockEndpoint mockOutputFile;

    @Before
    public void configure() throws Exception {
        var routeBuilder = new AdviceWithRouteBuilder() {
            @Override
            public void configure() {

                interceptSendToEndpoint(URI_FILE_RESULT).skipSendToOriginalEndpoint()
                        .to(MOCK_URI_FILE_RESULT);
            }
        };
        camelContext.getRouteDefinition(SEDA_PROCESS_ROUTE).adviceWith(camelContext, routeBuilder);

        mockOutputFile.reset();
    }

    @Test
    public void mustProcessFile() throws Exception {

        //Thread.sleep is necessary only when running all tests at same time by IntelliJ.
        //Running only this test in Intellij works without sleep
        //Running all tests by gradle works without sleep
        //Thread.sleep(500);

        final String inputFileContent =
                "001ç1234567891234çDiegoç50000\r\n" +
                        "001ç3245678865434çRenatoç40000.99\r\n" +
                        "002ç2345675434544345çJose da SilvaçRural\r\n" +
                        "002ç2345675433444345çEduardo PereiraçRural\r\n" +
                        "003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çDiego\r\n" +
                        "003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çRenato";

        final String expectedOutputFileContent =
                "amountSalesman=2\r\n" +
                        "amountCustomer=2\r\n" +
                        "worstSellers=[Renato]\r\n" +
                        "mostExpensiveSales=[10]";

        mockOutputFile.expectedBodiesReceived(expectedOutputFileContent);

        producerTemplate.sendBodyAndHeader(URI_FILE_INPUT,inputFileContent,
                Exchange.FILE_NAME, "arquivo1.dat");

        mockOutputFile.assertIsSatisfied();

    }

}