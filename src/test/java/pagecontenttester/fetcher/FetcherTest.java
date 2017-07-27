package pagecontenttester.fetcher;

import static org.assertj.core.api.Assertions.assertThat;
import static pagecontenttester.fetcher.FetchedPage.DeviceType.DESKTOP;

import java.io.IOException;
import java.util.Collections;

import org.jsoup.Connection;
import org.junit.Test;

public class FetcherTest {

    private Fetcher fetcher = Fetcher.builder().deviceType(DESKTOP).cookie(Collections.emptyMap()).build();

    private static final String VALID_URL = "https://github.com/christian-draeger";

    @Test
    public void fetcher_should_return_response_for_valid_url() throws IOException {
        Connection.Response response = fetcher.fetch(VALID_URL);
        assertThat(response.parse().title()).contains("GitHub");
    }
}
