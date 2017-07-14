package pagecontenttester.fetcher;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static pagecontenttester.fetcher.FetchedPage.DeviceType.DESKTOP;
import static pagecontenttester.fetcher.FetchedPage.DeviceType.MOBILE;

import org.junit.Test;

import pagecontenttester.annotations.Fetch;
import pagecontenttester.runner.PageContentTester;

public class FetchMultipleFromMethodMultipleTimesTest extends PageContentTester {

    @Test
    @Fetch(url = "github.com/christian-draeger")
    @Fetch(url = "www.idealo.de")
    public void can_fetch_from_method_annotation() {
        assertThat(page.get(0).getTitle(), containsString("GitHub"));
        assertThat(page.get(1).getTitle(), containsString("IDEALO"));
    }

    @Test
    @Fetch(url = "www.idealo.de")
    @Fetch(url = "github.com/christian-draeger")
    public void can_fetch_from_method_annotation2() {
        assertThat(page.get(0).getTitle(), containsString("IDEALO"));
        assertThat(page.get(1).getTitle(), containsString("GitHub"));
    }

    @Test
    @Fetch(url = "www.idealo.de")
    @Fetch(url = "www.idealo.de", device = MOBILE)
    public void can_fetch_from_method_annotation3() {
        assertThat(page.get(DESKTOP).getDeviceType(), is(DESKTOP));
        assertThat(page.get(MOBILE).getDeviceType(), is(MOBILE));
    }

}