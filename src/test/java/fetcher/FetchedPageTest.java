package fetcher;

import static fetcher.FetchedPage.fetchPage;
import static fetcher.FetchedPage.fetchPageAsMobileDevice;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.is;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import annotations.FetchPage;
import annotations.FetcherRule;

public class FetchedPageTest {

    private static FetchedPage fetchedPage;
    private static FetchedPage fetchedMobilePage;

    private static final String GITHUB_URL = "https://github.com/christian-draeger";
    private static final String GOOGLE_URL = "http://www.google.de";
    private static final String VALID_SELECTOR = "h1";

    @Rule
    public FetcherRule page = new FetcherRule();

    @BeforeClass
    public static void fetcher() {
        fetchedPage = fetchPage(GITHUB_URL);
        fetchedMobilePage = fetchPageAsMobileDevice(GITHUB_URL);
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
    @FetchPage("http://www.idealo.de")
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
    @FetchPage(GITHUB_URL)
    public void fetcher_should_return_count_of_certain_element() {
        assertThat(page.get().getElementCount(VALID_SELECTOR), is(1));
    }

    // fetch multiple pages by annotation
    @Test
    @FetchPage(GITHUB_URL)
    @FetchPage(GOOGLE_URL)
    public void fetch_multiple_pages_via_annotation_and_get_pages_by_index() {
        assertThat(page.get(0).isElementPresent("h1"), is(true));
        assertThat(page.get(0).getUrl(), equalTo(GITHUB_URL));

        assertThat(page.get(1).isElementPresent("#footer"), is(true));
        assertThat(page.get(1).getUrl(), equalTo(GOOGLE_URL));
    }

    @Test
    @FetchPage(GITHUB_URL)
    @FetchPage(GOOGLE_URL)
    public void fetch_multiple_pages_via_annotation_and_get_pages_by_url_snippet() {
        assertThat(page.get("github").isElementPresent("h1"), is(true));
        assertThat(page.get("google").isElementPresent("#footer"), is(true));
    }

    @Test
    public void fetch_multiple_pages_in_test_method() {
        FetchedPage github = fetchPage(GITHUB_URL);
        FetchedPage google = fetchPage(GOOGLE_URL);

        assertThat(github.isElementPresent("h1"), is(true));
        assertThat(github.getUrl(), equalTo(GITHUB_URL));

        assertThat(google.isElementPresent("#footer"), is(true));
        assertThat(google.getUrl(), equalTo(GOOGLE_URL));
    }

    @Test
    public void fetcher_should_return_true_for_certain_count_of_certain_element() {
        assertThat(fetchedPage.isElementPresentNthTimes(VALID_SELECTOR, 1), is(true));
    }

}
