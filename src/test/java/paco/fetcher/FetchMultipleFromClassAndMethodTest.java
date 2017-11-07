package paco.fetcher;

import org.junit.Test;
import paco.annotations.Fetch;
import paco.runner.Paco;

import static org.assertj.core.api.Assertions.assertThat;
import static paco.annotations.Fetch.Device.DESKTOP;
import static paco.annotations.Fetch.Protocol.HTTPS;

@Fetch(url = "localhost/example", port = "8089")
@Fetch(url = "localhost/example2", port = "8089")
public class FetchMultipleFromClassAndMethodTest extends Paco {

    @Test
    public void can_fetch_from_class_annotation() {
        assertThat(page.get(1).getTitle()).endsWith("title2");
        assertThat(page.get(0).getTitle()).endsWith("title");
    }

    @Test
    @Fetch(url = "localhost/example", port = "8089")
    @Fetch(url = "localhost/example3", port = "8089")
    public void can_fetch_from_method_even_if_fetched_from_class() {
        assertThat(page.get(0).getTitle()).endsWith("title");
        assertThat(page.get(1).getTitle()).endsWith("title3");
    }

    @Test
    @Fetch(protocol = HTTPS, url = "localhost/example", port = "8090")
    @Fetch(protocol = HTTPS, url = "localhost/example3", port = "8090")
    public void can_fetch_from_method_annotation() {
        assertThat(page.get("exam", DESKTOP).getTitle()).endsWith("title");
        assertThat(page.get(1).getTitle()).endsWith("title3");
    }

}