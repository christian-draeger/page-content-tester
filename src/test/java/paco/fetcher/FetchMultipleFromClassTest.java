package paco.fetcher;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import paco.annotations.Fetch;
import paco.runner.PageContentTester;

@Fetch(url = "localhost/example", port = "8089")
@Fetch(url = "localhost/example2", port = "8089")
public class FetchMultipleFromClassTest extends PageContentTester {

    @Test
    public void can_fetch_from_class_annotation() {
        assertThat(page.get(0).getTitle()).endsWith("title");
        assertThat(page.get(1).getTitle()).endsWith("title2");
    }

    @Test
    public void can_fetch_from_class_annotation2() {
        assertThat(page.get(1).getTitle()).endsWith("title2");
        assertThat(page.get(0).getTitle()).endsWith("title");
    }
}