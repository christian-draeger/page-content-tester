package pagecontenttester.fetcher;

import static org.assertj.core.api.Assertions.assertThat;
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
        assertThat(page.get(0).getTitle()).contains("GitHub");
        assertThat(page.get(1).getTitle()).contains("IDEALO");
    }

    @Test
    @Fetch(url = "www.idealo.de")
    @Fetch(url = "github.com/christian-draeger")
    public void can_fetch_from_method_annotation2() {
        assertThat(page.get(0).getTitle()).contains("IDEALO");
        assertThat(page.get(1).getTitle()).contains("GitHub");
    }

    @Test
    @Fetch(url = "www.idealo.de")
    @Fetch(url = "www.idealo.de", device = MOBILE)
    public void can_fetch_from_method_annotation3() {
        assertThat(page.get(DESKTOP).getDeviceType()).isEqualTo(DESKTOP);
        assertThat(page.get(MOBILE).getDeviceType()).isEqualTo(MOBILE);
    }

}