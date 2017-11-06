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

}