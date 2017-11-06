package paco.fetcher;

import org.jsoup.Connection;
import org.junit.Test;
import paco.runner.PageContentTester;

import java.io.IOException;
import java.util.Collections;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class FetcherTest extends PageContentTester {

    private Fetcher fetcher = new Fetcher();

    @Test
    public void fetcher_should_return_response_for_valid_url() throws IOException {
        Connection.Response response = fetcher.fetch(aValidRequest());
        assertThat(response.parse().title()).isEqualTo("i'm the title");
    }

    private Parameters aValidRequest() {
        return Parameters.builder()
                .urlToFetch("http://localhost:8089/example")
                .userAgent("")
                .requestBody("")
                .referrer("")
                .cookie(Collections.emptyMap())
                .method(Connection.Method.GET)
                .build();
    }
}
