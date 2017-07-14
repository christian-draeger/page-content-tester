package pagecontenttester.fetcher;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

import org.junit.Test;

import pagecontenttester.annotations.Fetch;
import pagecontenttester.runner.PageContentTester;

@Fetch(url = "github.com/christian-draeger")
@Fetch(url = "www.idealo.de")
public class FetchMultipleFromClassAndMethodTest extends PageContentTester {

    @Test
    public void can_fetch_from_class_annotation() {
        assertThat(page.get(1).getTitle(), containsString("IDEALO"));
        assertThat(page.get(0).getTitle(), containsString("GitHub"));
    }

    @Fetch(url = "www.google.de")
    @Fetch(url = "www.test.de")
    @Test
    public void can_fetch_from_class_annotation2() {
        assertThat(page.get(0).getTitle(), containsString("Google"));
        assertThat(page.get(1).getTitle(), containsString("test"));
    }

}