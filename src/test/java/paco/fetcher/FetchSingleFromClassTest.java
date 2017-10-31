package paco.fetcher;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import paco.annotations.Fetch;
import paco.runner.PageContentTester;

@Fetch(url = "localhost/example", port = "8089")
public class FetchSingleFromClassTest extends PageContentTester {

    @Test
    public void can_fetch_from_class_annotation() {
        assertThat(page.get().getTitle()).contains("i'm the title");
    }

}