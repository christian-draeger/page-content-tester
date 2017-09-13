package pagecontenttester.fetcher;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

import org.junit.BeforeClass;
import org.junit.Rule;

import com.github.tomakehurst.wiremock.junit.WireMockRule;

import pagecontenttester.runner.Paco;

public class WireMockConfig extends Paco {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8365);

    @BeforeClass
    public static void setUp() throws Exception {
        stubFor(get(urlEqualTo("/my/resource"))
                .withHeader("Accept", equalTo("text/xml"))
                .willReturn(aResponse()
                        .withStatus(123)
                        .withHeader("Content-Type", "text/xml")
                        .withBody("<response>Some content</response>")));
    }
}
