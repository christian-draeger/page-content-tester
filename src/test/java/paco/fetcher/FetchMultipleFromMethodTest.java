package paco.fetcher;

import org.junit.Test;
import paco.annotations.Fetch;
import paco.runner.Paco;

import static org.assertj.core.api.Assertions.assertThat;

public class FetchMultipleFromMethodTest extends Paco {

    @Test
    @Fetch(url = "localhost/example", port = "8089")
    @Fetch(url = "localhost/example2", port = "8089")
    public void can_fetch_from_method_annotation() {
        assertThat(page.get(1).getTitle()).endsWith("title2");
        assertThat(page.get(0).getTitle()).endsWith("title");
    }

    @Test
    @Fetch(url = "localhost/example", port = "8089")
    @Fetch(url = "localhost/somePath", port = "8089")
    public void fetch_multiple_pages_via_annotation_and_get_pages_by_url_contains() {
        assertThat(page.get("ex").getUrl()).isEqualTo("http://localhost:8089/example");
        assertThat(page.get("some").getUrl()).isEqualTo("http://localhost:8089/somePath");
    }
}
