package pagecontenttester.fetcher;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import pagecontenttester.annotations.Fetch;
import pagecontenttester.runner.PageContentTester;

@Fetch(url = "localhost/example", port = "8089")
@Fetch(url = "localhost/example2", port = "8089")
public class FetchMultipleFromClassAndMethodTest extends PageContentTester {

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