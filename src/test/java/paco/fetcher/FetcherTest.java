package paco.fetcher;

import org.jsoup.Connection;
import org.junit.Test;
import paco.runner.Paco;

import java.io.IOException;
import java.util.Collections;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class FetcherTest extends Paco {

    private Fetcher fetcher = new Fetcher();

    private static int counter = 0;

    @Test
    public void retry_on_connection_timeout() throws IOException {
        if (counter == 0) {
            counter++;
            fetcher.fetch(aValidRequest().timeout(1).build());
        }
        fetcher.fetch(aValidRequest().timeout(10000).build());
    }

    @Test
    public void fetcher_should_return_response_for_valid_url() throws IOException {
        Connection.Response response = fetcher.fetch(aValidRequest().build());
        assertThat(response.parse().title()).isEqualTo("i'm the title");
    }

    private Parameters.ParametersBuilder aValidRequest() {
        return Parameters.builder()
                .urlToFetch("http://localhost:8089/example")
                .userAgent("")
                .requestBody("")
                .referrer("")
                .cookie(Collections.emptyMap())
                .headers(Collections.emptyMap())
                .proxy(Collections.emptyMap())
                .method(Connection.Method.GET);
    }
}
