package paco.fetcher;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import paco.annotations.Fetch;
import paco.runner.PageContentTester;

public class FetchSingleFromMethodTest extends PageContentTester {

    @Test
    @Fetch(url = "localhost/example", port = "8089")
    public void can_fetch_from_method_annotation() {
        assertThat(page.get().getTitle()).contains("i'm the title");
    }

}