package pagecontenttester.fetcher;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.Ignore;
import org.junit.Test;

import pagecontenttester.annotations.Fetch;

public class WireMockTest extends WireMockConfig {

    @Ignore
    @Test
    @Fetch(url = "localhost:8365/my/resource")
    public void name() throws Exception {

        assertThat(page.get().getStatusCode()).isEqualTo(123);

    }
}
