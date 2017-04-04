package fetcher;

import static fetcher.FetchedPage.DeviceType.DESKTOP;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

import java.io.IOException;

import org.jsoup.Connection;
import org.junit.Test;

import runner.AbstractTest;

public class FetcherTest extends AbstractTest{

    Fetcher fetcher = Fetcher.builder().deviceType(DESKTOP).build();

    private static final String VALID_URL = "https://github.com/christian-draeger";

    @Test
    public void fetcher_should_return_response_for_valid_url() throws IOException {
        Connection.Response response = fetcher.fetch(VALID_URL);
        assertThat(response.parse().title(), containsString("GitHub"));
    }
}
