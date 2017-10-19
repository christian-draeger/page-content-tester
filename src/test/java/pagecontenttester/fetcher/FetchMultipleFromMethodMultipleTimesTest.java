package pagecontenttester.fetcher;

import static org.assertj.core.api.Assertions.assertThat;
import static pagecontenttester.fetcher.FetchedPage.DeviceType.DESKTOP;
import static pagecontenttester.fetcher.FetchedPage.DeviceType.MOBILE;

import org.junit.Test;

import pagecontenttester.annotations.Fetch;
import pagecontenttester.runner.PageContentTester;

public class FetchMultipleFromMethodMultipleTimesTest extends PageContentTester {

    @Test
    @Fetch(url = "localhost/example", port = "8089")
    @Fetch(url = "localhost/example2", port = "8089")
    public void can_fetch_from_method_annotation() {
        assertThat(page.get(0).getTitle()).endsWith("title");
        assertThat(page.get(1).getTitle()).endsWith("title2");
    }

    @Test
    @Fetch(url = "localhost/example2", port = "8089")
    @Fetch(url = "localhost/example", port = "8089")
    public void can_fetch_from_method_annotation2() {
        assertThat(page.get(0).getTitle()).endsWith("title2");
        assertThat(page.get(1).getTitle()).endsWith("title");
    }

    @Test
    @Fetch(url = "localhost/example", port = "8089")
    @Fetch(url = "localhost/example", port = "8089", device = MOBILE)
    public void can_fetch_from_method_annotation3() {
        assertThat(page.get(DESKTOP).getDeviceType()).isEqualTo(DESKTOP);
        assertThat(page.get(MOBILE).getDeviceType()).isEqualTo(MOBILE);
    }

}