package pagecontenttester.fetcher;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import pagecontenttester.annotations.Fetch;

public class WireMockTest extends WireMockConfig {

    @Test
    @Fetch(url = "localhost/templated", port = "8089")
    public void name() throws Exception {
        assertThat(page.get().getElement("body").text()).isEqualTo("Some content");
    }
}
