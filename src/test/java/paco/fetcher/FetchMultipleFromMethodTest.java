package paco.fetcher;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import paco.annotations.Fetch;
import paco.runner.PageContentTester;

public class FetchMultipleFromMethodTest extends PageContentTester {

    @Test
    @Fetch(url = "localhost/example", port = "8089")
    @Fetch(url = "localhost/example2", port = "8089")
    public void can_fetch_from_method_annotation() {
        assertThat(page.get(1).getTitle()).endsWith("title2");
        assertThat(page.get(0).getTitle()).endsWith("title");
    }

}