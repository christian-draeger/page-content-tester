package pagecontenttester.fetcher;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import pagecontenttester.annotations.Fetch;
import pagecontenttester.runner.PageContentTester;

public class FetchMultipleFromMethodTest extends PageContentTester {

    @Test
    @Fetch(url = "github.com/christian-draeger")
    @Fetch(url = "www.idealo.de")
    public void can_fetch_from_method_annotation() {
        assertThat(page.get(1).getTitle()).contains("IDEALO");
        assertThat(page.get(0).getTitle()).contains("GitHub");
    }

}