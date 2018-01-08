package paco.fetcher;

import org.junit.Test;
import paco.annotations.Fetch;
import paco.runner.Paco;

import static org.assertj.core.api.Assertions.assertThat;

public class FetchNotCachedTest extends Paco {

    @Test
    @Fetch(url = "localhost/example", port = "8089")
    public void fetching_same_page_will_be_cached() {
        assertThat(page.get().getTitle()).endsWith("title");
    }

    @Test
    @Fetch(url = "localhost/example", port = "8089")
    public void fetching_same_page_will_be_taken_from_cache() {
        assertThat(page.get().getTitle()).endsWith("title");
    }

    @Test
    @Fetch(url = "localhost/example", port = "8089", cacheDuplicate = false)
    public void fetching_same_page_will_not_be_taken_from_cache() {
        assertThat(page.get().getTitle()).endsWith("title");
    }

}
