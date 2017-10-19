package pagecontenttester.fetcher;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import pagecontenttester.annotations.Fetch;
import pagecontenttester.runner.PageContentTester;

public class WireMockTest extends PageContentTester {

    @Test
    @Fetch(url = "localhost/example", port = "8089")
    public void name() throws Exception {
        assertThat(page.get().getTitle()).contains("i'm the title");
    }
}
