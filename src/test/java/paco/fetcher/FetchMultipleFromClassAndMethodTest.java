package paco.fetcher;

import org.junit.Test;
import paco.annotations.Fetch;
import paco.runner.Paco;

import static org.assertj.core.api.Assertions.assertThat;

@Fetch(url = "localhost/example", port = "8089")
@Fetch(url = "localhost/example2", port = "8089")
public class FetchMultipleFromClassAndMethodTest extends Paco {

    @Test
    public void can_fetch_from_class_annotation() {
        assertThat(page.get(1).getTitle()).endsWith("title2");
        assertThat(page.get(0).getTitle()).endsWith("title");
    }

    @Fetch(url = "localhost/example", port = "8089")
    @Fetch(url = "localhost/example3", port = "8089")
    @Test
    public void can_fetch_from_class_annotation2() {
        assertThat(page.get(0).getTitle()).endsWith("title");
        assertThat(page.get(1).getTitle()).endsWith("title3");
    }

}