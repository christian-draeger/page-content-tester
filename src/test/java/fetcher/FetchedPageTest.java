package fetcher;

import static fetcher.FetchedPage.fetchPage;
import static fetcher.FetchedPage.fetchPageAsMobileDevice;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.is;

import org.junit.BeforeClass;
import org.junit.Test;

public class FetchedPageTest {

    private static FetchedPage fetchedPage;
    private static FetchedPage fetchedMobilePage;

    private static final String VALID_URL = "https://github.com/christian-draeger";
    private static final String VALID_SELECTOR = "h1";

    @BeforeClass
    public static void fetcher() {
        fetchedPage = fetchPage(VALID_URL);
        fetchedMobilePage = fetchPageAsMobileDevice(VALID_URL);
    }

    @Test
    public void fetcher_should_return_fetched_desktop_page_for_valid_url() {
        assertThat(fetchedPage.isMobile(), is(false));
    }

    @Test
    public void fetcher_should_return_fetched_mobile_page_for_valid_url() {
        assertThat(fetchedMobilePage.isMobile(), is(true));
    }

    @Test
    public void fetcher_should_return_referrer() {
        assertThat(fetchedPage.getConfig().getReferrer(), equalTo("http://www.google.com"));
    }

    @Test
    public void fetcher_should_return_cookie_value() {
        assertThat(fetchedPage.getCookieValue("logged_in"), equalTo("no"));
    }

    @Test
    public void fetcher_should_return_cookies() {
        assertThat(fetchedPage.getCookies(), hasEntry("logged_in", "no"));
    }

    @Test
    public void fetcher_should_return_element() {
        assertThat(fetchedPage.getElement(VALID_SELECTOR).hasText(), is(true));
    }

    @Test
    public void fetcher_should_return_status_code() {
        assertThat(fetchedPage.getStatusCode(), is(200));
    }

    @Test
    public void fetcher_should_return_true_if_certain_element_is_present() {
        assertThat(fetchedPage.isElementPresent(VALID_SELECTOR), is(true));
    }

    @Test
    public void fetcher_should_return_count_of_certain_element() {
        assertThat(fetchedPage.getElementCount(VALID_SELECTOR), is(1));
    }

    @Test
    public void fetcher_should_return_true_for_certain_count_of_certain_element() {
        assertThat(fetchedPage.isElementPresentNthTimes(VALID_SELECTOR, 1), is(true));
    }

}
