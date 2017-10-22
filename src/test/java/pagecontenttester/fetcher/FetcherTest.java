package pagecontenttester.fetcher;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import java.io.IOException;

import org.jsoup.Connection;
import org.junit.Test;

import pagecontenttester.runner.PageContentTester;

public class FetcherTest extends PageContentTester {

    private Fetcher fetcher = new Fetcher();

    private static final String VALID_URL = "http://localhost:8089/example";

    @Test
    public void fetcher_should_return_response_for_valid_url() throws IOException {
        Connection.Response response = fetcher.fetch(Parameters.builder()
                .urlToFetch(VALID_URL)
                .build());
        assertThat(response.parse().title()).isEqualTo("i'm the title");
    }
}
