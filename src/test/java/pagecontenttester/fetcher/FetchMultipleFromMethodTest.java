package pagecontenttester.fetcher;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

import org.junit.Test;

import pagecontenttester.annotations.Fetch;
import pagecontenttester.runner.PageContentTester;

public class FetchMultipleFromMethodTest extends PageContentTester {

    @Test
    @Fetch(url = "github.com/christian-draeger")
    @Fetch(url = "www.idealo.de")
    public void can_fetch_from_method_annotation() {
        assertThat(page.get(1).getTitle(), containsString("IDEALO"));
        assertThat(page.get(0).getTitle(), containsString("GitHub"));
    }

}