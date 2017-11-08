package paco.fetcher;

import org.junit.Test;
import paco.annotations.Fetch;
import paco.annotations.GetFetchedPageException;
import paco.runner.Paco;

import static org.assertj.core.api.Assertions.assertThat;
import static paco.annotations.Fetch.Device.DESKTOP;
import static paco.annotations.Fetch.Device.MOBILE;

public class FetchSingleFromMethodTest extends Paco {

    @Test
    @Fetch(url = "localhost/example")
    public void can_fetch_from_method_annotation() {
        assertThat(page.get().getTitle()).contains("i'm the title");
    }

    @Test
    @Fetch(url = "localhost/example")
    public void can_fetch_from_method_and_get_by_url_snippet() {
        assertThat(page.get("ex").getTitle()).contains("i'm the title");
    }

    @Test(expected = GetFetchedPageException.class)
    @Fetch(url = "localhost:8089/example", device = MOBILE)
    public void throw_exeption_on_wrong_device() {
        page.get(DESKTOP);
    }

    @Test(expected = GetFetchedPageException.class)
    @Fetch(url = "localhost:8089/example", device = MOBILE)
    public void throw_exeption_on_wrong_device_with_url_snippet() {
        page.get("example", DESKTOP);
    }

    @Test(expected = GetFetchedPageException.class)
    @Fetch(url = "localhost:8089/example", device = MOBILE)
    public void throw_exeption_on_wrong_url_snippet_with_device() {
        page.get("dfgfs", MOBILE);
    }

    @Test(expected = GetFetchedPageException.class)
    @Fetch(url = "localhost:8089/example", device = MOBILE)
    public void throw_exeption_on_wrong_url_snippet_and_wrong_device() {
        page.get("dfgfs", DESKTOP);
    }
}