package pagecontenttester.fetcher;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;

import com.github.tomakehurst.wiremock.junit.WireMockClassRule;

import pagecontenttester.runner.Paco;

public class WireMockConfig extends Paco {

    @ClassRule
    public static WireMockClassRule wireMockRule = new WireMockClassRule(8089);

    @Rule
    public WireMockClassRule instanceRule = wireMockRule;

    @BeforeClass
    public static void setUp() throws Exception {
        stubFor(get(urlEqualTo("/templated"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "text/html; charset=utf-8")
                        .withBody("<body>Some content</body>")));
    }
}
