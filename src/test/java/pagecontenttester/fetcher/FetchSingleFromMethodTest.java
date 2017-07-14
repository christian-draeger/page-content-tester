package pagecontenttester.fetcher;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

import org.junit.Test;

import pagecontenttester.annotations.Fetch;
import pagecontenttester.runner.PageContentTester;

public class FetchSingleFromMethodTest extends PageContentTester {

    @Test
    @Fetch(url = "www.idealo.de")
    public void can_fetch_from_method_annotation() {
        assertThat(page.get().getTitle(), containsString("IDEALO"));
    }

}